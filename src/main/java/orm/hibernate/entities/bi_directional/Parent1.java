package orm.hibernate.entities.bi_directional;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent1 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="parent_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	private Set<Child1> childs;
}

/* mappedBy="parent" indicates that the parent field in Child entity is "owning side"
 *  of the relationship */
