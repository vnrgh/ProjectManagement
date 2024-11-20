package example.spring.service;

import example.spring.controller.AuthController;
import example.spring.exception.EntityExceptionHandler;
import example.spring.model.Employee;
import example.spring.model.Role;
import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.RoleRepository;
import example.spring.repository.UserRepository;
import example.spring.security.model.UserDetailsImpl;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

//    @BeforeEach
//    void setUp() {
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("EMPLOYEE");
//
//        Employee employee = new Employee();
//        employee.setId(1L);
//    }

//    @Test
//    void createUserTest() {
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("EMPLOYEE");
//
//        Employee employee = new Employee();
//        employee.setId(1L);
//
//        when(roleRepository.findByName("EMPLOYEE")).thenReturn(role);
//        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
//
//        UserDTO userDTO = UserDTO.builder()
//                .email("email@gmail.com")
//                .username("Jane")
//                .password("password")
//                .employeeId(1L).build();
//
//        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
//            User user = invocation.getArgument(0);
//            user.setId(1L);
//            return user;
//        });
//        Long userId = userService.createUser(userDTO);
//
//        assertEquals(1L, userId);
//        verify(userRepository, times(1)).save(any(User.class));
//    }

//    @Test
//    void getUserByIdTest() {
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("EMPLOYEE");
//
//        List<Role> roles = List.of(role);
//
//        Employee employee = new Employee();
//        employee.setId(1L);
//
//        User user = User.builder()
//                .email("email@gmail.com")
//                .username("Jane")
//                .password("password")
//                .roles(roles)
//                .employee(employee).build();
//
//
//        when(userService.getUserById(1L)).thenReturn(user);
//        User returnedUser =  userService.getUserById(1L);
//
//        assertEquals(user, returnedUser);
//    }
//
//    @Test
//    void loadUserByUsernameTest() {
//        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(createMockUser()));
//        UserDetailsImpl userDetails = userService.loadUserByUsername("admin");
//        assertNotNull(userDetails);
//        assertEquals(2, userDetails.getAuthorities().size());
//        assertEquals("admin", userDetails.getUsername());
//    }
//
//    private User createMockUser() {
//        return User.builder()
//                .id(1L)
//                .username("admin")
//                .password("password")
//                .email("someemail@gg.com")
//                .roles(List.of(new Role(1L, "ADMIN"), new Role(2L, "EMPLOYEE")))
//                .build();
//    }
}
