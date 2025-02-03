package example.spring.service;

import example.spring.enums.Skill;
import example.spring.exception.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.model.User;
import example.spring.model.dto.EmployeeDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.TaskRepository;
import example.spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;


    @Test
    void createEmployeeTest() {
        Long userId = 1L;
        User user = new User(userId, "John", "password", "test@example.com", null, null);
        Employee employee = new Employee(1L, "John", "Doe", 20, 1000.0, Skill.JUNIOR, user);
        EmployeeDTO employeeDTO = new EmployeeDTO("John", "Doe", 20, 1000.0, Skill.JUNIOR, userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Long result = employeeService.createEmployee(employeeDTO);

        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result));

        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));
    }

    @Test
    void getEmployeeByIdTest() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", 20, 1000.0, Skill.JUNIOR, null);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result.getId()));
        assertEquals("John", result.getFirstName());
        assertEquals(Skill.JUNIOR, result.getSkill());

        Mockito.verify(employeeRepository).findById(employeeId);
    }

    @Test
    void getEmployeeByIdThrowsExceptionTest() {
        Long employeeId = 1L;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));

        Mockito.verify(employeeRepository).findById(employeeId);

    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = List.of(
                new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, null),
                new Employee(2L, "John2", "Doe2", 20, 9999.9, Skill.SENIOR, null)
        );
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result.get(0).getId()));
        assertEquals(Optional.of(2L), Optional.of(result.get(1).getId()));
        assertEquals(Skill.JUNIOR, result.get(0).getSkill());
        assertEquals(Skill.SENIOR, result.get(1).getSkill());

        Mockito.verify(employeeRepository).findAll();
    }

    @Test
    void updateEmployeeTest() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", 20, 1000.0, Skill.JUNIOR, null);
        EmployeeDTO employeeDTO = new EmployeeDTO("Elon", "Musk", 30, 9999.9, Skill.SENIOR, null);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        employeeService.updateEmployeeById(employeeId, employeeDTO);

        assertNotNull(employee);
        assertEquals(Optional.of(1L), Optional.of(employee.getId()));
        assertEquals("Elon", employee.getFirstName());
        assertEquals(9999.9, employee.getSalary(), 0.0001);
        assertEquals(Skill.SENIOR, employee.getSkill());

        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));
    }

    @Test
    void deleteEmployeeTest() {
        Long employeeId = 1L;
        Long userId = 1L;

        Mockito.when(taskRepository.existsByEmployeesId(employeeId)).thenReturn(false);

        employeeService.deleteEmployeeById(employeeId, userId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);
    }

    @Test
    void deleteEmployeeThrowsExceptionTest() {
        Long employeeId = 1L;
        Long userId = 1L;

       Mockito.when(taskRepository.existsByEmployeesId(employeeId)).thenReturn(true);

       assertThrows(IllegalStateException.class, () -> employeeService.deleteEmployeeById(employeeId, userId));

        Mockito.verify(userRepository, Mockito.never()).deleteById(Mockito.anyLong());
        Mockito.verify(employeeRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
