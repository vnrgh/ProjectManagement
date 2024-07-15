package example.spring.controller;

import example.spring.model.Employee;
import example.spring.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
//    @GetMapping("/salary/{salaryAmount}")
//    public List<Employee> employee(@PathVariable double salaryAmount) {
//        return employeeService.findBiggestSalary(employees, salaryAmount);
//    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
//
//    @PostMapping("/create")
//    public Employee createEmployee(Employee e) {
//
//    }
}
