package orm.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddress {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id", nullable=false)
    private Integer id;
	private String city;
	private String type; // Temporary, Permanent
	private int zip;
}
