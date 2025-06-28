package orm.hibernate.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orm.hibernate.dao.GenericDao;
import orm.hibernate.entities.HQLEntity;
import orm.hibernate.services.HQLService;

@RestController
@RequestMapping("api/hql")
public class HQLanguageApi {

	@Autowired
	private GenericDao dao;
	@Autowired
	private HQLService hqlService;
	
	
	@PostMapping
    public ResponseEntity<String> addEntity(@RequestBody List<HQLEntity> entities) {
		
		int id = dao.saveObjects(entities);
		
		if(id>0) {
			return new ResponseEntity<String>("HQL data saved", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Failed to save HQL data", HttpStatus.BAD_REQUEST);
    }
	
	@GetMapping("{id}")
    public ResponseEntity<HQLEntity> fetchHQLEntity(@PathVariable("id") Integer id) {
		
		HQLEntity result = dao.getObject(id, HQLEntity.class);
		
		return new ResponseEntity<HQLEntity>(result, HttpStatus.OK);
    }
	
	@GetMapping("firstLevelCache/{id}")
    public ResponseEntity<String> firstLevelCache(@PathVariable("id") Integer id) {
		
		String result = dao.testFirstLevelCache(id, HQLEntity.class);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
    }

	@GetMapping("secondLevelCache/{id}")
    public ResponseEntity<String> secondLevelCache(@PathVariable("id") Integer id) {
		
		String result = dao.testSecondLevelCache(id, HQLEntity.class);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
    }

	@GetMapping("testHqlQueries")
	public ResponseEntity<String> testHqlQueries() {
		
		int outcome = hqlService.testHQLQueries();
		
		if(outcome>0)
			return new ResponseEntity<String>("HQL Queries tested successfully", HttpStatus.OK);
		
		return new ResponseEntity<String>("Test failed in between", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("collectionToTable")
	public ResponseEntity<String> collectionToTable() {
		
		int outcome = hqlService.testCollectionTable();
		
		if(outcome>0)
			return new ResponseEntity<String>("HQL Queries tested successfully", HttpStatus.OK);
		
		return new ResponseEntity<String>("Test failed in between", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
