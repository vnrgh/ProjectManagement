package example.spring.service;

import example.spring.exception.UserNotFoundException;
import example.spring.model.Role;
import example.spring.model.User;
import example.spring.model.dto.UserRequestDTO;
import example.spring.model.dto.UserResponseDTO;
import example.spring.repository.RoleRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 1L;
    }

    @Test
    void createUserTest() {
        UserRequestDTO userDTO = UserRequestDTO.builder()
                .username("John")
                .password("password")
                .email("test@example.com")
                .build();
        Role role = new Role(1L, "EMPLOYEE");
        User user = new User(userId, "John", "password", "test@example.com", List.of(role), null);

        Mockito.when(roleRepository.findByName("EMPLOYEE")).thenReturn(role);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Long resultId = userService.createUser(userDTO);

        assertNotNull(resultId);
        assertEquals(1L, resultId);

        Mockito.verify(roleRepository).findByName("EMPLOYEE");
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void getUserByIdTest() {
        User mockedUser = new User(userId, "testUser", "password", "test@example.com", null, null);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockedUser));

        UserResponseDTO result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testUser", result.getUsername());

        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void getUserByIdThrowsExceptionTest() {
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void getAllUsersTest() {
        List<User> users = List.of(new User(1L, "testUser1", "password1", "test1@example.com", null, null),
                new User(2L, "testUser2", "password2", "test2@example.com", null, null));

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("testUser1", result.get(0).getUsername());
        assertEquals("testUser2", result.get(1).getUsername());

        Mockito.verify(userRepository).findAll();
    }
}
















