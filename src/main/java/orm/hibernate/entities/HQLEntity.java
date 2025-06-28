package orm.hibernate.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Don't use @Data, since it overrides equals()
 * This will always return true for Second Level Cache */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HQLEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Integer id;
	
	private String name;
	private Integer cost;
	private String city;
	private String country;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name="hql_hobbies",               // separate table
        joinColumns = @JoinColumn(name="id")  // FK back to HQLEntity
    )
    @Column(name = "hobby")	// column name in separate table
    private List<String> hobbies = new ArrayList<>();
	
	public HQLEntity(String name, String city) {
		this.name = name;
		this.city = city;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", name: " + name + ", cost: " + cost + ", city: " + city + ", country: " + country+"}";
	}
}
