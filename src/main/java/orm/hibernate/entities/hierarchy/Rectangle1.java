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
public class Rectangle1 extends Shape1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int Id;
	
	private double length, width;

	public Rectangle1(double length, double width) {
		this.length = length;
		this.width = width;
		this.type = "Rectangle";
	}

	@Override
	public double calculateArea() {
		
		return length * width;
	}
}
