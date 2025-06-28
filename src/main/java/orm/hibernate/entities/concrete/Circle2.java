package orm.hibernate.entities.concrete;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name="CIRCLE2")
public class Circle2 extends Shape2 {
	
	/* No Id for Table Per Concrete Class (Sub-Class) */
	
	private double radius;

	public Circle2(double radius) {
		this.radius = radius;
		this.type = "Circle";
	}

	@Override
	public double calculateArea() {
		
		area =  Math.PI * radius * radius;
		return area;
	}
}
