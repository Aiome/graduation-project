package top.aiome.controller.seckill.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.dao.seckill.entity.SeckillExample;
import top.aiome.redis.aspect.RequestLimit;
import top.aiome.service.seckill.service.Processor;
import top.aiome.service.seckill.service.SeckillInfo;
import top.aiome.service.seckill.service.interfaces.ISeckillSV;
import top.aiome.util.Constants;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
	@Autowired
	private ISeckillSV seckillSVImpl;
	
	private static Logger logger = LoggerFactory.getLogger(SeckillController.class);

	@Autowired
	private Processor preProcessor;


	@ResponseBody
	@RequestLimit(time = 3000)
	@RequestMapping(value="getSeckillById")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Map getSeckillById(SeckillInfo seckillInfo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {
			int result = preProcessor.preProcess(seckillInfo);
			if (result == Constants.Seckill.SECKILL_SUCCESS){
				map.put("promptMsg", "秒杀成功!");
			}else{
				map.put("promptMsg", "没库存啦，下次努力呀!");
			}
			map.put("seckill", result);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "秒杀失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("秒杀失败:", e);

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value="getSeckillsByPage")
	public Map getSeckills(Seckill seckill, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {
			/**
			 * 如下为两种分页处理方式（注意分页处理一定要在执行sql语句前进行设置！！）：
			 * 分页处理方式一：假设请求参数中包含分页参数pageNum和pageSize
			 * int pageNum = Integer.valueOf(request.getParameter("pageNum"));
			 * int pageSize = Integer.valueOf(request.getParameter("pageSize"));
			 * Page<Object> page = PageHelper.startPage(pageNum, pageSize);
			 */
			
	        /**
			 * 分页处理方式二：这种方式中请求参数名必须为pageNum,pageSize两个参数
			 * Page<Object> page = PageHelper.startPage(request);
			 */
			Page<Object> page = PageHelper.startPage(request);
			/**
			 * 自行定制，Where条件！！！！！！！！！！
			 */
			SeckillExample seckillExample = new SeckillExample();
			/*seckillExample.createCriteria().andNameEqualTo("zhangfei").andCodeEqualTo(seckill.getCode());
			seckillExample.setDistinct(true);
			seckillExample.setOrderByClause("id desc");*/
			List<Seckill> seckills = seckillSVImpl.getSeckills(seckillExample);
			
			/**
			 * 1、获取分页信息方式：
			 * System.out.println("符合条件的记录总数："+page.getTotal());
			 * System.out.println("每页记录数："+page.getPageSize());
			 * System.out.println("总页数："+page.getPages());
			 * 
			 * 2、如果希望获取更详细的分页信息可以使用PageInfo的方式，PageInfo中会包含更详细的分页相关信息：
			 * PageInfo<Student> pageInfo = new PageInfo<Student>(students);
			 */
			map.put("seckills", seckills);
			map.put("count", page.getTotal());
			map.put("result", "success");
			map.put("promptMsg", "查询秒杀成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "查询秒杀失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("查询秒杀信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="updateSeckillById")
	public Map updateSeckillById(Seckill seckill, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//seckill对象中封装所有需要update的属性信息。
		Map map = new HashMap();
		int count = 0;
		try {
			count = seckillSVImpl.updateSeckillById(seckill);
			map.put("result", "success");
			map.put("promptMsg", "更新秒杀成功!");
			if (count != 1) {
				map.put("result", "failed");
				map.put("promptMsg", "更新秒杀信息失败!");
			}
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "更新秒杀信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("更新秒杀信息失败。", e);
		}
		return map;
	}
	

	@ResponseBody
	@RequestMapping(value="updateSeckill")
	public Map updateSeckill(Seckill seckill, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//seckill对象中封装所有需要update的属性信息。
		Map map = new HashMap();
		int count = 0;
		try {
			/**
			 * 自行定制，Where条件！！！！！！！！！！
			 */
			SeckillExample seckillExample = new SeckillExample();
			/*
			 * seckillExample.createCriteria().andNameEqualTo("zhangfei");
			 */
			count = seckillSVImpl.updateSeckill(seckill, seckillExample);
			map.put("result", "success");
			map.put("promptMsg", "更新秒杀成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "更新秒杀信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("更新秒杀信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteSeckillById")
	public Map deleteSeckillById(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		int count = 0;
		try {
			count = seckillSVImpl.deleteSeckillById(id);
			map.put("result", "success");
			map.put("promptMsg", "删除秒杀成功!");
			if (count != 1) {
				map.put("result", "failed");
				map.put("promptMsg", "删除秒杀信息失败!");
			}
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "删除秒杀信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("删除秒杀信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteSeckill")
	public Map deleteSeckill(Seckill seckill, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		int count = 0;
		try {
			/**
			 * 自行定制，Where条件！！！！！！！！！！
			 */
			SeckillExample seckillExample = new SeckillExample();
			/*
			 * seckillExample.createCriteria().andNameEqualTo("zhangfei");
			 */
			count = seckillSVImpl.deleteSeckill(seckillExample);
			map.put("result", "success");
			map.put("promptMsg", "删除秒杀成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "删除秒杀信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("删除秒杀信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="insertSeckill")
	public Map insertSeckill(Seckill seckill, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//seckill对象中封装所有需要insert的属性信息。
		Map map = new HashMap();
		int count = 0;
		try {
			count = seckillSVImpl.insertSeckill(seckill);
			map.put("result", "success");
			map.put("promptMsg", "新增秒杀成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "新增秒杀信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("新增秒杀信息失败。", e);
		}
		return map;
	}
	

}
