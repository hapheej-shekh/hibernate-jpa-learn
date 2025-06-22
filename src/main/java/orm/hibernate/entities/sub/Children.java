package orm.hibernate.entities.sub;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Children {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="children_id", nullable=false)
    private Integer id;
	private String name;
	
	// Many Children has one Father
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "father_id")
	private Father father;
}
