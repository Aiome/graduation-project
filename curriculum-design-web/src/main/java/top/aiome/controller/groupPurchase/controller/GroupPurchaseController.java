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
	@Autowired
	private IGroupPurchaseSV groupPurchaseSVImpl;
	
	private static Logger logger = LoggerFactory.getLogger(GroupPurchaseController.class);

	@Autowired
	private Processor preProcessor;

	@RequestLimit(time = 500)
	@ResponseBody
	@RequestMapping(value="getGroupPurchaseInfo")
	public Map getGroupPurchaseById(Integer itemId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {

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
