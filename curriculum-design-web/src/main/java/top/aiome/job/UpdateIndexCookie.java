package top.aiome.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import top.aiome.dao.register.entity.Register;
import top.aiome.dao.register.entity.RegisterExample;
import top.aiome.service.register.service.interfaces.IRegisterSV;
import top.aiome.util.HttpBmccUtil;

public class UpdateIndexCookie {
	
	protected final transient Logger log = LoggerFactory.getLogger(UpdateIndexCookie.class);

	public static boolean isRuning = false;

	@Autowired
	private IRegisterSV registerSVImpl;

	protected void executeInternal(){
		log.info("保持充值页面登录状态开始");

		RegisterExample example = new RegisterExample();
		RegisterExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo((byte)-1);
		List<Register> registers = registerSVImpl.getRegisters(example);
		log.info("定时保持充值页面登录状态数据条数:" + registers.size());

		for (int i = 0; i < registers.size(); i++) {
			try {

				Map<String, String> refresh = HttpBmccUtil.loginRefresh(registers.get(i).getId().toString(), registers.get(i).getCookie());
				String cookie = refresh.get("setCookie");
				if (cookie.length() < 132){
				    throw new Exception("登录状态失效请重新登录!");
                }

				Register register = new Register();
				register.setId(registers.get(i).getId());
				register.setCookie(cookie);
				registerSVImpl.updateRegisterById(register);

			} catch (Exception e) {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost("smtp.163.com");//指定用来发送Email的邮件服务器主机名
                mailSender.setUsername("aiomeee@163.com");//用户名
                mailSender.setPassword("mixuema420");//密码

                Properties properties = new Properties();
                properties.setProperty("mail.smtp.auth", "true");// 开启认证
                properties.setProperty("mail.smtp.socketFactory.port", "465");// 设置ssl端口
                properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                mailSender.setJavaMailProperties(properties);


                SimpleMailMessage message = new SimpleMailMessage();//消息构造器
                message.setFrom("aiomeee@163.com");//发件人
                message.setTo("281169969@qq.com,aiomeee@foxmail.com".split(","));//收件人
                message.setSubject(registers.get(i).getId().toString() + " 保持充值页面登录状态失败！");//主题
                message.setText(registers.get(i).getId().toString() + " 保持充值页面登录状态失败！" + e);//正文
                mailSender.send(message);

                log.error(registers.get(i).getId().toString() + " 保持充值页面登录状态失败！" + e);
			}
		}
		log.info("保持充值页面登录状态结束");
	}
}
