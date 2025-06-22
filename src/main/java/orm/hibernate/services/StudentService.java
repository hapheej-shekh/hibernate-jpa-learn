package orm.hibernate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orm.hibernate.dao.StudentHibernateRepo;
import orm.hibernate.entities.Student;
import orm.hibernate.util.ObjectMapper;

@Service
public class StudentService {

	@Autowired
    private StudentHibernateRepo studentRepo;

	public orm.hibernate.beans.Student save(Student newStudent) {
		
		int outcome = studentRepo.save(newStudent);
		if(outcome != -1)
			newStudent.setId(outcome);
		
		return ObjectMapper.map(newStudent, orm.hibernate.beans.Student.class);
	}

	public Student findById(Integer id) {

		return studentRepo.findById(id);
	}

	public List<Student> findAll() {

		return studentRepo.findAll();
	}

	public void update(Student student) {

		studentRepo.update(student);
	}

	public void delete(Integer id) {

		studentRepo.delete(id);
	}
	
	public void deleteHql(Integer id) {

		studentRepo.deleteHql(id);
	}
}
