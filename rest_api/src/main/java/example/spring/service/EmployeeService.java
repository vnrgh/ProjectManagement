package example.spring.service;

import example.spring.exception.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Task;
import example.spring.model.User;
import example.spring.model.dto.EmployeeDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.TaskRepository;
import example.spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public EmployeeService (EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public Long createEmployee(EmployeeDTO employeeDTO) {
        Task task = taskRepository.findById(employeeDTO.getTaskId())
                .orElseThrow(() -> new EmployeeNotFoundException("Task with id " + employeeDTO.getTaskId() + " was not found"));

        Employee employee = Employee.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .age(employeeDTO.getAge())
                .skill(employeeDTO.getSkill())
                .salary(employeeDTO.getSalary())
                .task(task).build();
        return employeeRepository.save(employee).getId();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " was not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " was not found"));

        Task task = taskRepository.findById(employeeDTO.getTaskId())
                .orElseThrow(() -> new EmployeeNotFoundException("Task with id " + id + " was not found"));

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setAge(employeeDTO.getAge());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setSkill(employeeDTO.getSkill());
        existingEmployee.setTask(task);

        employeeRepository.save(existingEmployee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
