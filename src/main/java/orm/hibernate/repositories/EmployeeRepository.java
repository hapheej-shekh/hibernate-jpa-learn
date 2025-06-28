package orm.hibernate.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import orm.hibernate.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	/* Different Named Queries Example	*/
	
	// Basic filters
    List<Employee> findByName(String name);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findBySalaryBetween(Double min, Double max);

    // Ordering
    List<Employee> findAllByOrderBySalaryDesc();
    
    // Count
    long countByName(String name);

    // Fetch by Address city (assuming mapped correctly)
    @Query("SELECT DISTINCT e FROM Employee e JOIN e.address a WHERE a.city = :city")
    List<Employee> findByAddressCity(@Param("city") String city);

    // Pagination support
    Page<Employee> findBySalaryGreaterThan(Double salary, Pageable pageable);

    // Custom @Query with projection
    @Query("SELECT e.name, e.salary FROM Employee e WHERE e.salary > :salary")
    List<Object[]> findNameAndSalaryByMinSalary(@Param("salary") Double salary);
}

/*
| Category        | Example Method Signature                                     |
| --------------- | ------------------------------------------------------------ |
| By Name         | `List<Employee> findByName(String name)`                     |
| Salary Range    | `List<Employee> findBySalaryBetween(Double min, Double max)` |
| Count by Name   | `long countByName(String name)`                              |
| Order by Salary | `List<Employee> findAllByOrderBySalaryDesc()`                |
| Custom Join     | `@Query("... JOIN e.address a WHERE a.city = :city")`        |
| Select Specific | `@Query("SELECT e.name, e.salary ...")`                      |
| Pagination      | `Page<Employee> findBySalaryGreaterThan(..., Pageable)`      |
*/


