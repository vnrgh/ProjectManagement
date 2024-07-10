package example.spring.controller;

import example.spring.model.Employee;
import example.spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    @Autowired
    private List<Employee> employees;
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/salary/{salaryAmount}")
    public List<Employee> employee(@PathVariable double salaryAmount) {
        return employeeService.findBiggestSalary(employees, salaryAmount);
    }
}
