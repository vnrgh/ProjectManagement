package example.spring.controller;

import example.spring.enums.Skill;
import example.spring.exception.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Role;
import example.spring.model.User;
import example.spring.model.dto.SignInRequestDTO;
import example.spring.model.dto.SignInResponseDTO;
import example.spring.model.dto.UserDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.RoleRepository;
import example.spring.repository.UserRepository;
import example.spring.service.AuthService;
import example.spring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AuthService authService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    @BeforeEach
    void setup() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setAge(33);
        employee.setSalary(2000.0);
        employee.setSkill(Skill.JUNIOR);
        employeeRepository.save(employee);
    }

    @Test
    void connectionEstablished() {
        assertThat(container.isCreated()).isTrue();
        assertThat(container.isRunning()).isTrue();
    }

    @Test
    void authServiceSignUpTest() {
        List<Role> roles = Collections.singletonList(roleRepository.findByName("EMPLOYEE"));

        UserDTO userDTO = UserDTO.builder()
                .username("test_name")
                .password("test_password")
                .email("test@mail.com")
                .employeeId(1L)
                .build();

        Long userId = userService.createUser(userDTO);

        assertThat(userId).isNotNull();
        assertThat(userRepository.findById(userId)).isPresent();
        User user = userRepository.findById(userId).orElseThrow();
        user.setRoles(roles);
        assertThat(user.getUsername()).isEqualTo("test_name");
        assertThat(user.getEmployee().getId()).isEqualTo(1L);
        assertEquals(1, user.getRoles().size());
        assertThat(user.getRoles().get(0).getName()).isEqualTo("EMPLOYEE");
    }

    @Test
    void signup_throwsExceptionWhenEmployeeNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test_name");
        userDTO.setPassword("test_password");
        userDTO.setEmail("test@mail.com");
        userDTO.setEmployeeId(999L); // Несуществующий ID сотрудника

        assertThrows(EmployeeNotFoundException.class, () -> userService.createUser(userDTO));
    }
















//    @Test
//    void authServiceSignInTest() {
//        // Arrange
//        SignInRequestDTO signInRequestDTO = new SignInRequestDTO("admin", "pass");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<SignInRequestDTO> request = new HttpEntity<>(signInRequestDTO, headers);
//
//        // Act
//        ResponseEntity<SignInResponseDTO> response = restTemplate.exchange(
//                "/auth/signin",
//                HttpMethod.POST,
//                request,
//                SignInResponseDTO.class
//        );
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        SignInResponseDTO responseBody = response.getBody();
//        assertNotNull(responseBody);
//        assertNotNull(responseBody.getAccessToken());
//    }

}


