package example.spring.service;

import example.spring.exception.EmployeeNotFoundException;
import example.spring.exception.UserNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Task;
import example.spring.model.User;
import example.spring.model.dto.EmployeeDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.TaskRepository;
import example.spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public EmployeeService (EmployeeRepository employeeRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Long createEmployee(EmployeeDTO employeeDTO) {
        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + employeeDTO.getUserId() + " not found"));

        Employee employee = Employee.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .age(employeeDTO.getAge())
                .skill(employeeDTO.getSkill())
                .salary(employeeDTO.getSalary())
                .user(user)
                .build();

        employee = employeeRepository.save(employee);

        user.setEmployee(employee);
        userRepository.save(user);

        return employee.getId();
    }


    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setAge(employeeDTO.getAge());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setSkill(employeeDTO.getSkill());

        employeeRepository.save(existingEmployee);
    }

    public void deleteEmployeeById(Long id, Long userId) {
        if (taskRepository.existsByEmployeesId(id)) {
            throw new IllegalStateException("Employee with unfinished task(s) cannot be deleted");
        }

        userRepository.deleteById(userId);
        employeeRepository.deleteById(id);
    }
}
