package example.spring.service;

import example.spring.config.TestContainerConfig;
import example.spring.model.dto.SignInRequestDTO;
import example.spring.model.dto.SignInResponseDTO;
import example.spring.model.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
class AuthServiceIT {
    @Autowired
    private AuthService authService;

    @Test
    void signUpTest() {
        UserRequestDTO userDTO = new UserRequestDTO("user", "password", "test@mail.com");
        Long id = authService.signUp(userDTO);

        assertNotNull(id);
        assertEquals(2L, id);
    }

    @Test
    void signInTest() {
        SignInRequestDTO requestDTO = new SignInRequestDTO("admin", "pass");
        SignInResponseDTO response = authService.signIn(requestDTO);

        assertNotNull(response.getAccessToken());
    }

    @Test
    void signInThrowsExceptionTest() {
        SignInRequestDTO requestDTO = new SignInRequestDTO("wrongUser", "wrongPassword");

        assertThrows(BadCredentialsException.class, () -> authService.signIn(requestDTO));
    }
}
