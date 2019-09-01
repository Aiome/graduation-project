package top.aiome.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import top.aiome.dao.skillInfo.entity.SkillInfo;
import top.aiome.dao.skillInfo.entity.SkillInfoExample;
import top.aiome.service.skillInfo.service.interfaces.ISkillInfoSV;
import top.aiome.util.HttpBmccUtil;

public class UpdateLqCookie {
	
	protected final transient Logger log = LoggerFactory.getLogger(UpdateLqCookie.class);

	public static boolean isRuning = false;

	@Autowired
	private ISkillInfoSV skillInfoSVImpl;

	protected void executeInternal(){
		log.info("获取领券登录状态开始");

		List<SkillInfo> infos = skillInfoSVImpl.getSkillInfos(new SkillInfoExample());
		log.info(System.currentTimeMillis() + " 获取领券领券登录状态数据条数:" + infos.size());

		for (int i = 0; i < infos.size(); i++) {
			try {

                Map<String, String> lqCookie = HttpBmccUtil.getLqCookie(infos.get(i).getVerify());
                String cookie = lqCookie.get("setCookie");
                String csrf = lqCookie.get("csrf");

                SkillInfo skillInfo = new SkillInfo();
                skillInfo.setCsrf(csrf);
                skillInfo.setCookie(cookie);
                skillInfo.setId(infos.get(i).getId());

                skillInfoSVImpl.updateSkillInfoById(skillInfo);

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
                message.setSubject(infos.get(i).getId().toString() + e.getMessage() + " 抢券登录状态失败！" );//主题
                message.setText(infos.get(i).getId().toString() + e.getMessage() + " 抢券登录状态失败！" + e);//正文
                mailSender.send(message);

                log.error(infos.get(i).getId().toString() + " 抢券登录状态失败！" + e);
			}
		}
		log.info("保持领券登录状态结束");
	}
}
