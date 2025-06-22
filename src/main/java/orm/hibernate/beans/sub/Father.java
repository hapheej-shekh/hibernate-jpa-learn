package orm.hibernate.beans.sub;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Father {

    private Integer id;
	private String name;
	
	private List<Children> childrens;
}
