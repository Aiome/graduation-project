package top.aiome.controller.groupPurchase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.aiome.redis.aspect.RequestLimit;
import top.aiome.service.groupPurchase.service.interfaces.GroupInfo;
import top.aiome.service.groupPurchase.service.interfaces.IGroupPurchaseSV;
import top.aiome.service.seckill.service.Processor;

@Controller
@RequestMapping("/groupPurchase")
public class GroupPurchaseController {
	//Spring 注入团购的服务类
	@Autowired
	private IGroupPurchaseSV groupPurchaseSVImpl;
	
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
	@RequestLimit(time = 500) //限时 500毫秒内 相同IP或者相同用户只能调用一次该接口
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

}
