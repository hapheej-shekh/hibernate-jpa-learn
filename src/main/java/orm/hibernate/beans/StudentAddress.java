package orm.hibernate.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAddress {

    private Integer id;
	private String city;
	private String type; // Temporary, Permanent
	private int zip;
}
