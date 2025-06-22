package orm.hibernate.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {

    private Integer id;
	private String name;
	private float fee;
}
