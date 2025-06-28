package orm.hibernate.entities.bi_directional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wife1 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="wife_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToOne(mappedBy = "wife") // Inverse side
    private Husband1 husband;
}
