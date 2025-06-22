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

import orm.hibernate.beans.Library;
import orm.hibernate.services.LibraryService;
import orm.hibernate.util.ObjectMapper;

@RestController
@RequestMapping("/api/library")
public class LibraryApi {

	@Autowired
	private LibraryService libraryService;
	
	
	@PostMapping
    public ResponseEntity<Library> addStudent(@RequestBody Library library) {
		
		orm.hibernate.entities.Library newStudent = 
				ObjectMapper.map(library, orm.hibernate.entities.Library.class);
		
		libraryService.save(newStudent);
        
        return ResponseEntity.ok(library);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Library> getById(@PathVariable Integer id) {
		
		
		orm.hibernate.entities.Library library = libraryService.findById(id);
        
        return ResponseEntity.ok(ObjectMapper.map(library, Library.class));
    }

    @GetMapping
    public ResponseEntity<List<Library>> getAll() {
    	
    	List<Library> outcome = new ArrayList<>();
    	
    	List<orm.hibernate.entities.Library> all = libraryService.findAll();
    	
    	all.forEach(lib->{
    		outcome.add(ObjectMapper.map(lib, Library.class));
    	});
        return ResponseEntity.ok(outcome);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateStudent(
    		@PathVariable Integer id, @RequestBody Library library) {
    	
    	orm.hibernate.entities.Library outcome = libraryService.findById(id);
        
        if (outcome==null) 
        	return ResponseEntity.notFound().build();

        outcome.setFee(library.getFee());
        outcome.setName(library.getName());
        
        libraryService.update(outcome);
        
        return ResponseEntity.ok(library);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
    	
    	
        if (libraryService.findById(id)==null)
        	return ResponseEntity.notFound().build();
        
        libraryService.delete(id);
        
        return ResponseEntity.ok("Library not available, id: "+id);
    }
}
