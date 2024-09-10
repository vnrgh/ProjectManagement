package example.spring.service;

import example.spring.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService (EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getEmployeeById(id).orElseThrow(() -> new EmployeeNotFoundException("account with id " + id + " was not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public Long createEmployee(Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteEmployeeById(id);
    }

    public void updateEmployeeById(Long id, Employee employee) {
        employeeRepository.updateEmployeeById(id, employee);
    }
}
