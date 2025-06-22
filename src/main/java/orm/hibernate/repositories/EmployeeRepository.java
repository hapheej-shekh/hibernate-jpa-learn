package orm.hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import orm.hibernate.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
