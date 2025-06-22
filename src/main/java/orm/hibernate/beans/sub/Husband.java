package orm.hibernate.beans.sub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Husband {

    private Integer id;
	private String name;	
	private Wife wife;
}
