package orm.hibernate.entities.sub;

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
public class Relative {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="relative_id", nullable=false)
    private Integer id;
	private String name;	
}
