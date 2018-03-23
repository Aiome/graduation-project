package top.aiome.service.test.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import top.aiome.service.test.service.interfaces.IStudentSV;
import top.aiome.test.dao.StudentMapper;
import top.aiome.test.entity.Student;
import top.aiome.test.entity.StudentExample;

@Service
public class StudentSVImpl implements IStudentSV {
	@Resource
	private StudentMapper studentMapper;

	@Override
	public Student getStudentById(Long id) {
		return studentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Student> getStudents(StudentExample studentExample) {
		return studentMapper.selectByExample(studentExample);
	}

	@Override
	public int updateStudentById(Student student) {
		return studentMapper.updateByPrimaryKeySelective(student);
	}
	
	@Override
	public int updateStudent(Student student, StudentExample studentExample) {
		return studentMapper.updateByExampleSelective(student, studentExample);
	}

	@Override
	public int insertStudent(Student student) {
		return studentMapper.insertSelective(student);
	}

	@Override
	public int deleteStudentById(Long id) {
		return studentMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteStudent(StudentExample studentExample) {
		return studentMapper.deleteByExample(studentExample);
	}
}
