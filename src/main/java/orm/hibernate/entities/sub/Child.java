package orm.hibernate.entities.sub;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Child {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="child_id", nullable=false)
    private Integer id;
	private String name;
	
	/* Child belongs to one Parent
       Foreign key referencing the Parent table */
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id") // Foreign key column in Parent table
	private Parent parent;
}
