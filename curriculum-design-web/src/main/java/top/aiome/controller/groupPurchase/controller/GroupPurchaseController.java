package top.aiome.controller.groupPurchase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.aiome.dao.skillInfo.entity.SkillInfo;
import top.aiome.dao.skillInfo.entity.SkillInfoExample;
import top.aiome.redis.aspect.RequestLimit;
import top.aiome.service.groupPurchase.service.interfaces.GroupInfo;
import top.aiome.service.groupPurchase.service.interfaces.IGroupPurchaseSV;
import top.aiome.service.register.service.interfaces.IRegisterSV;
import top.aiome.service.seckill.service.Processor;
import top.aiome.service.skillInfo.service.interfaces.ISkillInfoSV;
import top.aiome.util.HttpBmccUtil;

@Controller
@RequestMapping("/register")
public class GroupPurchaseController {
	//Spring 注入团购的服务类
	@Autowired
	private IGroupPurchaseSV groupPurchaseSVImpl;

	@Autowired
	private IRegisterSV registerSVImpl;

	@Autowired
	private ISkillInfoSV skillInfoSVImpl;
	
	private static Logger logger = LoggerFactory.getLogger(GroupPurchaseController.class);

	@Autowired
	private Processor preProcessor;

	/**
	 * 获取团购列表接口
	 * @param itemId  商品ID
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestLimit(time = 5000,count = 2) //限时 500毫秒内 相同IP或者相同用户只能调用一次该接口
	@ResponseBody
	@RequestMapping(value="getGroupPurchaseInfo")//获取团购列表
	public Map getGroupPurchaseById(Integer itemId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {

			//调用 服务层 获取团购信息列表
			List<GroupInfo> info = groupPurchaseSVImpl.getGroupInfo(itemId);

			map.put("groupPurchase", info);
			map.put("result", "success");
			map.put("promptMsg", "查询团购信息成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "查询团购失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("查询团购信息失败。", e);
		}
		return map;
	}
	//团购接口
	@ResponseBody
	@RequestMapping(value="buyByGroupId")
	public Map buyByGroupId(Integer groupId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {

			groupPurchaseSVImpl.buyByGroupId(groupId);

			map.put("result", "success");
			map.put("promptMsg", "团购成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "团购失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("团购失败。", e);
		}
		return map;
	}

	//验证码
	@ResponseBody
	@RequestMapping(value="sms")
	public Map sms(String phone, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {
			Map<String,String> param = new HashMap<>();
			param.put("reqUrl","sms");
			param.put("method","sendSms");
			param.put("mobile",phone);

			Map<String, String> result = HttpBmccUtil.doPost("http://he.10086.cn/app/ecu/ecuAction.do", param, null);
			logger.info(phone + " " + result.get("bmccResult"));

            JSONObject ss = JSON.parseObject(result.get("bmccResult"));
            if (null == ss || ss.getIntValue("resultCode") != 1){
                throw new Exception(ss.toString());
            }

			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("errorMsg", "1001：" + phone + "操作失败，请将错误信息截图发送至代理" + e.getMessage());
			logger.error("发送验证码失败", e);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value="login")
	public Map login(String phone,String smsCode, String tag, Integer target, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		try {
			registerSVImpl.all(phone,smsCode,tag,target);
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", e.getMessage());
			logger.error("登记失败。", e);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value="getLqCookie")
	public Map login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		try {
			Page<Object> page = PageHelper.startPage(request);

			List<SkillInfo> infos = skillInfoSVImpl.getSkillInfos(new SkillInfoExample());
			String start = skillInfoSVImpl.getStartTime();



			map.put("result", "success");
			map.put("startTime", start);
			map.put("data",infos);
			map.put("total", page.getTotal());
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", e.getMessage());
			logger.error("登记失败。", e);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value="setTime")
	public Map login(String time, String offset, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		try {
			String res;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Date date = simpleDateFormat.parse(time);
			long ts = date.getTime() / 1000;
			res = String.valueOf(ts) + offset;
			skillInfoSVImpl.setStartTime(res);

			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", e.getMessage());
			logger.error("登记失败。", e);
		}
		return map;
	}

}
