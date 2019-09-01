package top.aiome.service.register.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import top.aiome.dao.register.RegisterMapper;
import top.aiome.dao.register.entity.Register;
import top.aiome.dao.register.entity.RegisterExample;
import top.aiome.dao.skillInfo.SkillInfoMapper;
import top.aiome.dao.skillInfo.entity.SkillInfo;
import top.aiome.service.register.service.interfaces.IRegisterSV;
import top.aiome.util.HttpBmccUtil;

@Service
public class RegisterSVImpl implements IRegisterSV{
	@Resource
	private RegisterMapper registerMapper;

	@Autowired
	private SkillInfoMapper skillInfoMapper;

	private static Logger logger = LoggerFactory.getLogger(RegisterSVImpl.class);

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Boolean all(String phone, String smsCode, String tag, Integer target) throws Exception {
		Map<String,String> param = new HashMap<>();
		Map<String, String> smsResult = new HashMap<>();
		Map<String, String> mdlResult = new HashMap<>();
		Map<String, String> lqResult = new HashMap<>();
		//验证是否登记
		if (phone == null || smsCode == null){
			throw new Exception("手机号或验证码不能为空！");
		}

		if (tag == null){
			throw new Exception("请联系上级代理重新索要登记链接！");
		}

		Register register = this.getRegisterById(Long.valueOf(phone));
		SkillInfo skillInfo = skillInfoMapper.selectByPrimaryKey(Long.valueOf(phone));

		try{
			logger.info("====" + phone + "====开始登录====验证码====" + smsCode);

			smsResult = HttpBmccUtil.login(phone, smsCode);

			logger.info("====" + phone + "====登录结果====" + smsResult);
			JSONObject result = JSON.parseObject(smsResult.get("bmccResult"));
			if (null == result || result.getIntValue("resultCode") != 1){
				throw new Exception("bmccResult" + result.toString());
			}

			logger.info("====" + phone + "====登录验证码结果====" + smsResult.get("bmccResult"));
			logger.info("====" + phone + "====登录验证码Cookie要落库的数据====" + smsResult.get("setCookie"));

			if (register != null){
				register.setTag(tag);
				register.setTarget(target);
				register.setCookie(smsResult.get("setCookie"));
				register.setCreatTime(System.currentTimeMillis()/1000);
				registerMapper.updateByPrimaryKeySelective(register);
			}else {
				Register register1 = new Register();
				register1.setId(Long.valueOf(phone));
				register1.setCookie(smsResult.get("setCookie"));
				register1.setTarget(target);
				register1.setTag(tag);
				register1.setCreatTime(System.currentTimeMillis()/1000);
				registerMapper.insertSelective(register1);
			}

		} catch (Exception e){
			logger.error("====" + phone + "====登录失败：" + e);
			throw new Exception("1002：" + phone + "操作失败，请将错误信息截图发送至代理" + e);
		}



		try{
			logger.info("====" + phone + "====开始获取抢券verifyStr====");

			mdlResult = HttpBmccUtil.mdl(phone, smsResult.get("setCookie"));

			logger.info("====" + phone + "====获取抢券verifyStr 结果====" + smsResult);

			JSONObject result = JSON.parseObject(mdlResult.get("bmccResult"));
			if (null == result || result.getIntValue("resultCode") != 1){
				throw new Exception("bmccResult" + result.toString());
			}

			logger.info("====" + phone + "====获取抢券verifyStr结果====" + mdlResult.get("bmccResult"));
			logger.info("====" + phone + "====获取抢券verifyStr要落库的数据====" + mdlResult.get("verifyStr"));
		} catch (Exception e){
			logger.error("====" + phone + "====获取抢券Cookie：" + e);
			throw new Exception("1003：" + phone + "操作失败，请将错误信息截图发送至代理 " + e.getMessage());
		}

		try{
			logger.info("====" + phone + "====开始获取抢券Cookie====");

			lqResult = HttpBmccUtil.getLqCookie(mdlResult.get("verifyStr"));

			logger.info("====" + phone + "====获取抢券Cookie要落库的数据csrf====" + lqResult.get("csrf"));
			logger.info("====" + phone + "====获取抢券Cookie要落库的数据cookie====" + lqResult.get("setCookie"));

			if (skillInfo != null){
				skillInfo.setCsrf(lqResult.get("csrf"));
				skillInfo.setVerify(mdlResult.get("verifyStr"));
				skillInfo.setCsrf(lqResult.get("csrf"));
				skillInfo.setCookie(lqResult.get("setCookie"));
				skillInfo.setCreateTime(System.currentTimeMillis()/1000);
				skillInfoMapper.updateByPrimaryKeySelective(skillInfo);

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
				message.setSubject(skillInfo.getId().toString() + " 重新登记抢券Cookie！ 代理 " + tag);//主题
				message.setText(skillInfo.getId().toString() + " 重新登记抢券Cookie！ 代理 " + tag);//正文
				mailSender.send(message);
			}else {
				SkillInfo skillInfo1 = new SkillInfo();
				skillInfo1.setId(Long.valueOf(phone));
				skillInfo1.setVerify(mdlResult.get("verifyStr"));
				skillInfo1.setCsrf(lqResult.get("csrf"));
				skillInfo1.setCookie(lqResult.get("setCookie"));
				skillInfo1.setCreateTime(System.currentTimeMillis()/1000);
				skillInfoMapper.insertSelective(skillInfo1);

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
				message.setSubject(skillInfo1.getId().toString() + " 登记登记！ 代理 " + tag);//主题
				message.setText(skillInfo1.getId().toString() + " 登记成功！ 代理 " + tag);//正文
				mailSender.send(message);
			}
		} catch (Exception e){
			logger.error("====" + phone + "====获取抢券Cookie失败：" + e);
			throw new Exception("1004：" + phone + "操作失败，请将错误信息截图发送至代理 " + e);
		}



		return true;
	}

	@Override
	public Register getRegisterById(Long id) {
		return registerMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Register> getRegisters(RegisterExample registerExample) {
		return registerMapper.selectByExample(registerExample);
	}

	@Override
	public int updateRegisterById(Register register) {
		return registerMapper.updateByPrimaryKeySelective(register);
	}
	
	@Override
	public int updateRegister(Register register, RegisterExample registerExample) {
		return registerMapper.updateByExampleSelective(register, registerExample);
	}

	@Override
	public int insertRegister(Register register) {
		return registerMapper.insertSelective(register);
	}

	@Override
	public Long deleteRegisterById(Long id) {
		return registerMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteRegister(RegisterExample registerExample) {
		return registerMapper.deleteByExample(registerExample);
	}
}
