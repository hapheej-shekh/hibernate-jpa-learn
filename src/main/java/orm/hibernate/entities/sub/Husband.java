package orm.hibernate.entities.sub;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Husband {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="husband_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "wife_id")  // FK in Wife table
	private Wife wife;
}
