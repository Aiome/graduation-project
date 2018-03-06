package top.aiome.test.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.aiome.test.entity.Student;
import top.aiome.test.entity.StudentExample;
import top.aiome.test.service.interfaces.IStudentSV;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private IStudentSV studentSVImpl;

	private static Logger logger = LoggerFactory.getLogger(StudentController.class);  
	
	@ResponseBody
	@RequestMapping(value="get/{id}")
	public Map getStudentById(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		try {
			Student student = studentSVImpl.getStudentById(id);
			map.put("student", student);
			map.put("result", "success");
			map.put("promptMsg", "查询测试可删成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "查询测试可删失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("查询测试可删信息失败。", e);
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value="getStudentsByPage")
	public Map getStudents(Student student, HttpServletRequest request, HttpServletResponse response) throws IOException{
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
			StudentExample studentExample = new StudentExample();
			/*studentExample.createCriteria().andNameEqualTo("zhangfei").andCodeEqualTo(student.getCode());
			studentExample.setDistinct(true);
			studentExample.setOrderByClause("id desc");*/
			List<Student> students = studentSVImpl.getStudents(studentExample);
			
			/**
			 * 1、获取分页信息方式：
			 * System.out.println("符合条件的记录总数："+page.getTotal());
			 * System.out.println("每页记录数："+page.getPageSize());
			 * System.out.println("总页数："+page.getPages());
			 * 
			 * 2、如果希望获取更详细的分页信息可以使用PageInfo的方式，PageInfo中会包含更详细的分页相关信息：
			 * PageInfo<Student> pageInfo = new PageInfo<Student>(students);
			 */
			map.put("students", students);
			map.put("count", page.getTotal());
			map.put("result", "success");
			map.put("promptMsg", "查询测试可删成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "查询测试可删失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("查询测试可删信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="updateStudentById")
	public Map updateStudentById(Student student, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//student对象中封装所有需要update的属性信息。
		Map map = new HashMap();
		int count = 0;
		try {
			count = studentSVImpl.updateStudentById(student);
			map.put("result", "success");
			map.put("promptMsg", "更新测试可删成功!");
			if (count != 1) {
				map.put("result", "failed");
				map.put("promptMsg", "更新测试可删信息失败!");
			}
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "更新测试可删信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("更新测试可删信息失败。", e);
		}
		return map;
	}
	

	@ResponseBody
	@RequestMapping(value="updateStudent")
	public Map updateStudent(Student student, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//student对象中封装所有需要update的属性信息。
		Map map = new HashMap();
		int count = 0;
		try {
			/**
			 * 自行定制，Where条件！！！！！！！！！！
			 */
			StudentExample studentExample = new StudentExample();
			/*
			 * studentExample.createCriteria().andNameEqualTo("zhangfei");
			 */
			count = studentSVImpl.updateStudent(student, studentExample);
			map.put("result", "success");
			map.put("promptMsg", "更新测试可删成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "更新测试可删信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("更新测试可删信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteStudentById")
	public Map deleteStudentById(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		int count = 0;
		try {
			count = studentSVImpl.deleteStudentById(id);
			map.put("result", "success");
			map.put("promptMsg", "删除测试可删成功!");
			if (count != 1) {
				map.put("result", "failed");
				map.put("promptMsg", "删除测试可删信息失败!");
			}
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "删除测试可删信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("删除测试可删信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteStudent")
	public Map deleteStudent(Student student, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		int count = 0;
		try {
			/**
			 * 自行定制，Where条件！！！！！！！！！！
			 */
			StudentExample studentExample = new StudentExample();
			/*
			 * studentExample.createCriteria().andNameEqualTo("zhangfei");
			 */
			count = studentSVImpl.deleteStudent(studentExample);
			map.put("result", "success");
			map.put("promptMsg", "删除测试可删成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "删除测试可删信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("删除测试可删信息失败。", e);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="insertStudent")
	public Map insertStudent(Student student, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//student对象中封装所有需要insert的属性信息。
		Map map = new HashMap();
		int count = 0;
		try {
			count = studentSVImpl.insertStudent(student);
			map.put("result", "success");
			map.put("promptMsg", "新增测试可删成功!");
		} catch (Exception e) {
			map.put("result", "failed");
			map.put("promptMsg", "新增测试可删信息失败!");
			map.put("errorMsg", e.getMessage());
			logger.error("新增测试可删信息失败。", e);
		}
		return map;
	}
	

}
