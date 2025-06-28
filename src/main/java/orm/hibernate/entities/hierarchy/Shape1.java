package orm.hibernate.entities.hierarchy;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
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
// Table per Hierarchy -> InheritanceType.SINGLE_TABLE [Also known Single Table Inheritance]
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) // Optional, default is SINGLE_TABLE
//Column name to show Class-Name, default is DTYPE
@DiscriminatorColumn(name="shape_type", discriminatorType=DiscriminatorType.STRING)
@Table(name="SHAPE1")
public class Shape1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int Id;
	
	public String type;
	public double area;

	public Shape1() {
		this.type = "Shape";
	}

	public double calculateArea() {
		return 0; // default implementation. 
	}
}
