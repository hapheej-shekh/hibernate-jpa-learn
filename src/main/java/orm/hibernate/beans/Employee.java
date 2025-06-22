package orm.hibernate.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Integer id;
    private String name;
    private double salary;
    private List<EmployeeAddress> address;
}
