package orm.hibernate.beans.sub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Children {

    private Integer id;
	private String name;
	private Father father;
}
