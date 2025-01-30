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
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        employeeService.deleteEmployeeById(id, userId);
        return new ResponseEntity<>("Employee successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployeeById(id, employeeDTO);
        return new ResponseEntity<>("Employee with id " + id + " successfully updated", HttpStatus.OK);
    }
}
