package orm.hibernate.entities;

import java.util.HashSet;
import java.util.Set;

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
public class Course {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="course_id", nullable=false)
	private int id;
	private String name;
	private float fee;
	
	@ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
	
	/* The mappedBy = "courses" tells Hibernate:
	 * Don’t create a separate join table from this side
	 * The owning side (Student) already manages the association 
	 * Without mappedBy, Hibernate will try to create a second join table */
}
