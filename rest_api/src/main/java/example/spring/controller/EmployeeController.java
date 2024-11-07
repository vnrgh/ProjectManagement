package example.spring.controller;

import example.spring.model.Employee;
import example.spring.model.dto.EmployeeDTO;
import example.spring.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping()
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long result = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Employee was successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployeeById(id, employeeDTO);
        return new ResponseEntity<>("Employee with id " + id + " was successfully updated", HttpStatus.OK);
    }
}
