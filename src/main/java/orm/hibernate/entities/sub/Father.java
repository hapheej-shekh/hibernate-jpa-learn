package orm.hibernate.entities.sub;

import java.util.ArrayList;
import java.util.List;

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
public class Father {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="father_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="father")
	private List<Children> childrens = new ArrayList<>();
}
