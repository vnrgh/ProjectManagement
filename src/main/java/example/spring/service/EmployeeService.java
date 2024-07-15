package example.spring.service;

import example.spring.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService (EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id).orElseThrow(() -> new EmployeeNotFoundException("account with id " + id + " was not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees().orElseThrow(() -> new EmployeeNotFoundException("no employee was found"));
    }

//    public Employee createEmployee(Employee employee) {
//        return employeeRepository.save(employee);
//    }



    public List<Employee> findBiggestSalary(List<Employee> employees, double salary) {
        return employees.stream()
                .filter(e -> e.getSalary() > salary)
                .toList();
    }

}
