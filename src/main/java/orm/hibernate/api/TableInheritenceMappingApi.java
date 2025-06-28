package orm.hibernate.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orm.hibernate.dao.TableInheritanceMappingDao;
import orm.hibernate.entities.concrete.Circle2;
import orm.hibernate.entities.concrete.Rectangle2;
import orm.hibernate.entities.hierarchy.Circle1;
import orm.hibernate.entities.hierarchy.Rectangle1;
import orm.hibernate.entities.hierarchy.Shape1;
import orm.hibernate.entities.subclass.Circle3;
import orm.hibernate.entities.subclass.Rectangle3;

@RestController
@RequestMapping("api/inheritence")
public class TableInheritenceMappingApi {

	@Autowired
	private TableInheritanceMappingDao tableDao;
	
	/* Table Per Class in Inheritence */
	
	@PostMapping("tablePerHierarchy")
    public ResponseEntity<String> addShape1(@RequestBody Map<String, String> shape) {
				
		if(shape.containsKey("radius")) {
			
			double radious = Double.parseDouble(shape.get("radius"));
			
			Circle1 c1 = new Circle1(radious);
			c1.setArea(c1.calculateArea());
			
			if(tableDao.save(c1)>0)
				return ResponseEntity.ok("Circle persisted");
			else
				return ResponseEntity.ok("Failed to persist Circle");
			
		} else if(shape.containsKey("width") && shape.containsKey("length")) {

			double width = Double.parseDouble(shape.get("width"));
			double length = Double.parseDouble(shape.get("length"));
			
			Rectangle1 r1 = new Rectangle1(length, width);
			r1.setArea(r1.calculateArea());
			
			if(tableDao.save(r1)>0)
				return ResponseEntity.ok("Shape persisted");
			else
				return ResponseEntity.ok("Failed to persist Shape");
			
		} else if(shape.isEmpty()) {

			Shape1 s1 = new Shape1();
			s1.setArea(0);
			
			if(tableDao.save(s1)>0)
				return ResponseEntity.ok("Shape persisted");
			else
				return ResponseEntity.ok("Failed to persist Shape");
			
		} else
			return ResponseEntity.ok("Input not identified");
	}

	/* Table Per Sub-Class (Concrete class) in Inheritence */
	
	@PostMapping("tablePerConcrete")
	public ResponseEntity<String> addShape2(@RequestBody Map<String, String> shape) {
		
		if(shape.containsKey("radius")) {
			
			double radious = Double.parseDouble(shape.get("radius"));
			
			Circle2 c2 = new Circle2(radious);
			c2.setArea(c2.calculateArea());
			
			if(tableDao.save(c2)>0)
				return ResponseEntity.ok("Circle persisted");
			else
				return ResponseEntity.ok("Failed to persist Circle");
			
		} else if(shape.containsKey("width") && shape.containsKey("length")) {

			double width = Double.parseDouble(shape.get("width"));
			double length = Double.parseDouble(shape.get("length"));
			
			Rectangle2 r2 = new Rectangle2(length, width);
			r2.setArea(r2.calculateArea());
			
			if(tableDao.save(r2)>0)
				return ResponseEntity.ok("Rectangle persisted");
			else
				return ResponseEntity.ok("Failed to persist Rectangle");
			
		} else 
			return ResponseEntity.ok("Input not identified");
	}

	/* Table Per Joins [Sub class tables + joining info table for FK] in Inheritence */
	
	@PostMapping("tablePerJoins")
	public ResponseEntity<String> addShape3(@RequestBody Map<String, String> shape) {
		
		if(shape.containsKey("radius")) {
			
			double radious = Double.parseDouble(shape.get("radius"));
			
			Circle3 c3 = new Circle3(radious);
			c3.setArea(c3.calculateArea());
			
			if(tableDao.save(c3)>0)
				return ResponseEntity.ok("Circle persisted");
			else
				return ResponseEntity.ok("Failed to persist Circle");
			
		} else if(shape.containsKey("width") && shape.containsKey("length")) {

			double width = Double.parseDouble(shape.get("width"));
			double length = Double.parseDouble(shape.get("length"));
			
			Rectangle3 r3 = new Rectangle3(length, width);
			r3.setArea(r3.calculateArea());
			
			if(tableDao.save(r3)>0)
				return ResponseEntity.ok("Rectangle persisted");
			else
				return ResponseEntity.ok("Failed to persist Rectangle");
			
		} else 
			return ResponseEntity.ok("Input not identified");
	}
}
