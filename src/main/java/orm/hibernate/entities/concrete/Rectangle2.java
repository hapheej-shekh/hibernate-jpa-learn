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
@Table(name="RECTANGLE2")
public class Rectangle2 extends Shape2 {

	/* No Id for Table Per Concrete Class (Sub-Class) */
	
	private double length, width;

	public Rectangle2(double length, double width) {
		this.length = length;
		this.width = width;
		this.type = "Rectangle";
	}

	@Override
	public double calculateArea() {
		
		return length * width;
	}
}
