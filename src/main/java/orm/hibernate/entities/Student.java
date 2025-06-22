package orm.hibernate.entities;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id", nullable=false)
    private Integer id;
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "address_id")  // FK in Student table
	private StudentAddress address;
	
	//CascadeType.ALL-> saves referenced objects as well, but don't delete, since not owned user
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id")
	private Library library;
	
	//CascadeType.ALL-> saves referenced objects as well, but don't delete, since not owned user
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
	private Set<Course> courses = new HashSet<>();
}
