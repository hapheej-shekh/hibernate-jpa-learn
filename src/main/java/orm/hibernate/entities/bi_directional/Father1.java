package orm.hibernate.entities.bi_directional;

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
public class Father1 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="father_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToMany(mappedBy="father", cascade=CascadeType.ALL)
	private List<Children1> childrens = new ArrayList<>();
}
//mappedBy="father" tells Hibernate that Children entity owns relationship