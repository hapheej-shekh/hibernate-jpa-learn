package orm.hibernate.entities.subclass;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Table(name="CIRCLE3")
public class Circle3 extends Shape3 {
	
	/* Id not required for Table Per Subclass (JOINs) */
	
	private double radius;

	public Circle3(double radius) {
		this.radius = radius;
		this.type = "Circle";
	}

	@Override
	public double calculateArea() {
		
		area =  Math.PI * radius * radius;
		return area;
	}
}
