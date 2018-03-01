package top.aiome.test.service.interfaces;

import java.util.List;
import top.aiome.test.entity.Student;
import top.aiome.test.entity.StudentExample;

/**
 * 	测试可删管理
 */
public interface IStudentSV {
	/**
	 * 根据ID查询测试可删信息
	 * @param id 测试可删ID
	 * @return 测试可删实体
	 */
	public Student getStudentById(Long id);

	/**
	 * 查询测试可删信息
	 * @param studentExample 查询条件
	 * @return 测试可删列表
	 */
	public List<Student> getStudents(StudentExample studentExample);

	/**
	 * 根据ID更新测试可删信息
	 * @param student 测试可删信息
	 * @return 更新数量
	 */
	public int updateStudentById(Student student); 
	
	/**
	 * 根据条件更新测试可删信息
	 * @param student  测试可删信息
	 * @param studentExample  查询条件
	 * @return 更新数量
	 */
	public int updateStudent(Student student, StudentExample studentExample); 
	
	/**
	 * 根据ID删除测试可删信息
	 * @param id 测试可删ID
	 * @return 删除数量
	 */
	public int deleteStudentById(Long id);
	
	/**
	 * 根据条件删除测试可删信息
	 * @param studentExample 查询条件
	 * @return 删除数量
	 */
	public int deleteStudent(StudentExample studentExample);
	
	/**
	 * 新增测试可删信息
	 * @param student 测试可删信息
	 * @return 新增数量
	 */
	public int insertStudent(Student student);
	
}


