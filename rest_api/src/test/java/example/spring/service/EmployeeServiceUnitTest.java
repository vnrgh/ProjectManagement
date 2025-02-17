package example.spring.service;

import example.spring.enums.Skill;
import example.spring.exception.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.model.User;
import example.spring.model.dto.EmployeeDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.TaskRepository;
import example.spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private EmployeeDTO employeeDTO;
    private User user;
    private Long userId;
    private Long employeeId;

    @BeforeEach
    void setUp() {
        employeeId = 1L;
        userId = 1L;
        employeeDTO = new EmployeeDTO("John", "Doe", 20, 1000.0, Skill.JUNIOR, userId);
        user = new User(userId, "John", "password", "test@example.com", null, null);
    }


    @Test
    void createEmployeeTest() {
        Employee employee = new Employee(1L, "John", "Doe", 20, 1000.0, Skill.JUNIOR, user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Long result = employeeService.createEmployee(employeeDTO);

        assertNotNull(result);
        assertEquals(1L, result);

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getEmployeeByIdTest() {
        Employee employee = new Employee(employeeId, "John", "Doe", 20, 1000.0, Skill.JUNIOR, null);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());

        verify(employeeRepository).findById(employeeId);
    }

    @Test
    void getEmployeeByIdThrowsExceptionTest() {
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));

        verify(employeeRepository).findById(employeeId);
    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = List.of(
                new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, null),
                new Employee(2L, "John2", "Doe2", 20, 9999.9, Skill.SENIOR, null)
        );
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(employeeRepository).findAll();
    }

    @Test
    void updateEmployeeTest() {
        Employee employee = new Employee(employeeId, "Elon", "Doe", 20, 9999.9, Skill.SENIOR, null);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        employeeService.updateEmployeeById(employeeId, employeeDTO);

        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("John", employee.getFirstName());

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void deleteEmployeeTest() {
        when(taskRepository.existsByEmployeesId(employeeId)).thenReturn(false);

        employeeService.deleteEmployeeById(employeeId, userId);

        verify(userRepository, times(1)).deleteById(userId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void deleteEmployeeThrowsExceptionTest() {
       when(taskRepository.existsByEmployeesId(employeeId)).thenReturn(true);

       assertThrows(IllegalStateException.class, () -> employeeService.deleteEmployeeById(employeeId, userId));

       verify(userRepository, never()).deleteById(Mockito.anyLong());
       verify(employeeRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
