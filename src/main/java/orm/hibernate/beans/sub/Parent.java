package orm.hibernate.beans.sub;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent {
	
    private Integer id;
	private String name;	
	private Set<Child> childs;
}
