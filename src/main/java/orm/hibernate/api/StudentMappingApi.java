package orm.hibernate.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orm.hibernate.beans.Student;
import orm.hibernate.entities.Library;
import orm.hibernate.services.StudentService;
import orm.hibernate.util.ObjectMapper;

@RestController
@RequestMapping("/api/students")
public class StudentMappingApi {

	@Autowired
	private StudentService studentService;
	
	/* OneToOne-> for student address
	 * ManyToOne-> for library 
	 * ManyToMany-> for courses */
	@PostMapping("multiMapping")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		
		orm.hibernate.entities.Student newStudent = 
				ObjectMapper.map(student, orm.hibernate.entities.Student.class);
		
		Student savedStudent = studentService.save(newStudent);
        
        return ResponseEntity.ok(savedStudent);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Integer id) {
		
		
		orm.hibernate.entities.Student student = studentService.findById(id);
        
        return ResponseEntity.ok(ObjectMapper.map(student, Student.class));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
    	
    	List<Student> outcome = new ArrayList<>();
    	
    	List<orm.hibernate.entities.Student> all = studentService.findAll();
    	
    	all.forEach(std->{
    		outcome.add(ObjectMapper.map(std, Student.class));
    	});
        return ResponseEntity.ok(outcome);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
    	
    	orm.hibernate.entities.Student outcome = studentService.findById(id);
        
        if (outcome==null) 
        	return ResponseEntity.notFound().build();

        outcome.setName(student.getName());
        outcome.setLibrary(ObjectMapper.map(student.getLibrary(), Library.class));
        outcome.setAddress(outcome.getAddress());

        studentService.update(outcome);
        
        return ResponseEntity.ok(ObjectMapper.map(outcome, Student.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
    	
    	
        if (studentService.findById(id)==null)
        	return ResponseEntity.notFound().build();
        
        studentService.deleteHql(id);
        
        return ResponseEntity.ok("Student deleted, id: "+id);
    }
}
