package orm.hibernate.api;

import java.util.List;
import java.util.Optional;

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

import orm.hibernate.entities.Employee;
import orm.hibernate.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeMappingApi {

	@Autowired
	private EmployeeService empService;
	
	/* OneToMany-> for employee address [temp, permanent] */
	
	// One Employee -> Many Address
	@PostMapping("OneToMany")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		
        Employee saved = empService.save(employee);
        
        return ResponseEntity.ok(saved);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id) {
		
        Optional<Employee> optional = empService.findById(id);
        
        return optional.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
    	
        return ResponseEntity.ok(empService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee updatedData) {
    	
        Optional<Employee> optional = empService.findById(id);
        
        if (optional.isEmpty()) 
        	return ResponseEntity.notFound().build();

        Employee existing = optional.get();
        existing.setName(updatedData.getName());
        existing.setSalary(updatedData.getSalary());

        return ResponseEntity.ok(empService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    	
        if (!empService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        empService.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
}
