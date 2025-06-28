package orm.hibernate.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orm.hibernate.services.CriteriaQueryService;

@RestController
@RequestMapping("api/criteria")
public class CriteriaQueryApi {

	@Autowired
	private CriteriaQueryService criteria;
	
	
	@GetMapping
	public ResponseEntity<String> testHqlQueries() {
		
		int outcome = criteria.testHQLQueries();
		
		if(outcome>0)
			return new ResponseEntity<String>("HQL Queries tested successfully", HttpStatus.OK);
		
		return new ResponseEntity<String>("Test failed in between", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
