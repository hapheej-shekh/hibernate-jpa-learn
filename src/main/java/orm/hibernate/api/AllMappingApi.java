package orm.hibernate.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orm.hibernate.beans.sub.Children;
import orm.hibernate.beans.sub.Family;
import orm.hibernate.beans.sub.Husband;
import orm.hibernate.beans.sub.Parent;
import orm.hibernate.services.AllMappingService;

@RestController
@RequestMapping("/api/mappings")
public class AllMappingApi {

	@Autowired
	private AllMappingService mappingService;
	
	
	@PostMapping("oneToOne")
	public ResponseEntity<String> oneToOne(@RequestBody Husband husband) {
		
		int status = mappingService.saveHusband(husband);
		
		if(status>0)
			return ResponseEntity.ok("Husband detail saved");
		
		return ResponseEntity.ok("Failed to persist info");
	}
	
	@PostMapping("oneToMany")
	public ResponseEntity<String> oneToMany(@RequestBody Parent parent) {
		
		int status = mappingService.saveParent(parent);
		
		if(status>0)
			return ResponseEntity.ok("Parent detail saved");
		
		return ResponseEntity.ok("Failed to persist info");
	}
	
	@PostMapping("manyToOne")
	public ResponseEntity<String> manyToOne(@RequestBody Children children) {
		
		int status = mappingService.saveChildren(children);
		
		if(status>0)
			return ResponseEntity.ok("Children detail saved");
		
		return ResponseEntity.ok("Failed to persist info");
	}
	
	@PostMapping("manyToMany")
	public ResponseEntity<String> manyToMany(@RequestBody Family family) {
		
		int status = mappingService.saveFamily(family);
		
		if(status>0)
			return ResponseEntity.ok("Family detail saved");
		
		return ResponseEntity.ok("Failed to persist info");
	}
}
