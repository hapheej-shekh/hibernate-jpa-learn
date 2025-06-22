package orm.hibernate.beans;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import orm.hibernate.entities.Course;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;
	private String name;
	
	private StudentAddress address;
	private Library library;
	private Set<Course> courses;
}
