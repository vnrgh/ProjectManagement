package example.spring.service;

import example.spring.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    public List<Employee> findBiggestSalary(List<Employee> employees, double salary) {
        return employees.stream()
                .filter(e -> e.getSalary() > salary)
                .toList();
    }

}
