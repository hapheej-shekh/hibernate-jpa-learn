package orm.hibernate.entities.bi_directional;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Family1 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="family_id", nullable=false)
    private Integer id;
	private String name;
	
	@ManyToMany(cascade ={CascadeType.ALL})
    @JoinTable(
        name = "familyrelative",
        joinColumns=@JoinColumn(name = "family_id"), 
        inverseJoinColumns=@JoinColumn(name = "relative_id")
    )
	private Set<Relative1> relatives = new HashSet<>();
}
