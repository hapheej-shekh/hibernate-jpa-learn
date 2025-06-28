package orm.hibernate.entities.bi_directional;

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
public class Husband1 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="husband_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="wife_id") //This also makes Husband1 the owning side, FK goes in Husband table
	private Wife1 wife;
}
