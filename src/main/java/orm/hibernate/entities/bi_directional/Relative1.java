package orm.hibernate.entities.bi_directional;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relative1 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="relative_id", nullable=false)
    private Integer id;
	private String name;
	
	@ManyToMany(mappedBy = "relatives", cascade=CascadeType.ALL)	// Bi-directional
    private Set<Family1> families = new HashSet<>();
}
