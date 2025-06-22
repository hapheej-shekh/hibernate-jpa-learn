package orm.hibernate.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id", nullable=false)
    private Integer id;
    private String name;
    private double salary;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<EmployeeAddress> address = new ArrayList<>();	// Temp, Permanent
    
    public Employee(String name, double salary) {
    	this.name = name;
    	this.salary = salary;
    }
}
