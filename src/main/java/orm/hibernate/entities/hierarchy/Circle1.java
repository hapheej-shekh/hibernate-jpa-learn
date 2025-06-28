package orm.hibernate.entities.hierarchy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Circle1 extends Shape1 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int Id;
	
	private double radius;

	public Circle1(double radius) {
		this.radius = radius;
		this.type = "Circle";
	}

	@Override
	public double calculateArea() {
		
		area =  Math.PI * radius * radius;
		return area;
	}
}
