package orm.hibernate.entities.concrete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
// Table Per Concrete
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) // Optional, default is SINGLE_TABLE
public class Shape2 {

	/* AUTO lets JPA decide based on DB dialect
	 * H2/MySQL-> IDENTITY, PostgreSQL/Oracle-> May use SEQUENCE
	 * Since Id generates for each sub-class too, thats why let JPA decide */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // Use AUTO here!
	public int Id;
	public String type;
	public double area;

	public Shape2() {
		this.type = "Shape";
	}

	public double calculateArea() {
		return 0; // default implementation. 
	}
}
