package orm.hibernate.entities.subclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Inheritance(strategy=InheritanceType.JOINED) // Optional, default is SINGLE_TABLE
@Table(name="SHAPE3")
public class Shape3 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int Id;
	
	public String type;
	public double area;

	public Shape3() {
		this.type = "Shape";
	}

	public double calculateArea() {
		return 0; // default implementation. 
	}
}
