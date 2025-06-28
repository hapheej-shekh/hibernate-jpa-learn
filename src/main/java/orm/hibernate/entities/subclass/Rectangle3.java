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
@Table(name="RECTANGLE3")
public class Rectangle3 extends Shape3 {

	/* Id not required for Table Per Subclass (JOINs) */
	
	private double length, width;

	public Rectangle3(double length, double width) {
		this.length = length;
		this.width = width;
		this.type = "Rectangle";
	}

	@Override
	public double calculateArea() {
		
		return length * width;
	}
}
