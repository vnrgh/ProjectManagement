package example.spring.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.spring.config.TestContainerConfig;
import example.spring.enums.Skill;
import example.spring.model.Employee;
import example.spring.model.User;
import example.spring.model.dto.EmployeeDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.TaskRepository;
import example.spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "username", "password", "test@example.com", null, null);
    }

    @Test
    void createEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO("John", "Doe", 20, 1000.0, Skill.JUNIOR, 1L);

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        Employee employee = new Employee(1L, "John", "Doe", 20, 1000.0, Skill.JUNIOR, user);

        Employee savedEmployee = employeeRepository.save(employee);

        mockMvc.perform(get("/api/employee/{id}", savedEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedEmployee.getId()))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getAllEmployeesTest() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, user));

        employeeRepository.saveAll(employees);

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John1"));
    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, user);
        Employee savedEmployee = employeeRepository.save(employee);
        String expectedMessage = "Employee with id " + savedEmployee.getId() + " successfully updated";
        EmployeeDTO employeeDTO = new EmployeeDTO("Elon", "Musk", 30, 9999.9, Skill.SENIOR, user.getId());

        mockMvc.perform(put("/api/employee/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    void deleteEmployeeById() throws Exception {
        taskRepository.deleteAll();
        User savedUser = userRepository.save(user);
        Employee savedEmployee = employeeRepository.save(new Employee(999L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, savedUser));

        savedUser.setEmployee(savedEmployee);
        userRepository.save(savedUser);
        String expectedMessage = "Employee successfully deleted";

        mockMvc.perform(delete("/api/employee/" + savedEmployee.getId() + "/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }
}
