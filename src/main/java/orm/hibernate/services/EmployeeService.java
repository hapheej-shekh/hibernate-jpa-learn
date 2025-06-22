package orm.hibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orm.hibernate.entities.Employee;
import orm.hibernate.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository empRepository;

	public Employee save(Employee employee) {

		return empRepository.save(employee);
	}

	public Optional<Employee> findById(Integer id) {

		return empRepository.findById(id);
	}

	public List<Employee> findAll() {

		return empRepository.findAll();
	}

	public boolean existsById(Integer id) {

		Optional<Employee> outcome = empRepository.findById(id);
		
		return outcome.isPresent();
	}

	public void deleteById(Integer id) {

		empRepository.deleteById(id);
	}
}
