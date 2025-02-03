package example.spring.controller;

import example.spring.enums.Skill;
import example.spring.model.Employee;
import example.spring.model.dto.EmployeeDTO;
import example.spring.security.jwt.TokenProvider;
import example.spring.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@MockBean(TokenProvider.class)
@AutoConfigureMockMvc(addFilters = false)  // disabling spring security
class EmployeeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO("John", "Doe", 20, 1000.0, Skill.JUNIOR, null);
        Long expectedId = 1L;

        Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(expectedId);

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedId.toString()));

        Mockito.verify(employeeService, Mockito.times(1)).createEmployee(Mockito.any(EmployeeDTO.class));
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", 20, 1000.0, Skill.JUNIOR, null);

        Mockito.when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        mockMvc.perform(get("/api/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.salary").value(1000.0))
                .andExpect(jsonPath("$.skill").value(Skill.JUNIOR.name()));

        Mockito.verify(employeeService).getEmployeeById(employeeId);
    }

    @Test
    void getAllEmployeesTest() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, null),
                new Employee(2L, "John2", "Doe2", 20, 9999.9, Skill.SENIOR, null)
        );

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John1"))
                .andExpect(jsonPath("$[1].firstName").value("John2"));

        Mockito.verify(employeeService).getAllEmployees();
    }

    @Test
    void updateEmployee() throws Exception {
        Long employeeId = 1L;
        String expectedMessage = "Employee with id " + employeeId + " successfully updated";
        EmployeeDTO employeeDTO = new EmployeeDTO("Elon", "Musk", 30, 9999.9, Skill.SENIOR, null);

        mockMvc.perform(put("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));

        Mockito.verify(employeeService, Mockito.times(1)).updateEmployeeById(employeeId, employeeDTO);
    }

    @Test
    void deleteEmployeeById() throws Exception {
        Long employeeId = 1L;
        Long userId = 2L;
        String expectedMessage = "Employee successfully deleted";

        mockMvc.perform(delete("/api/employee/{id}/{userId}", employeeId, userId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));

        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployeeById(employeeId, userId);
    }
}
