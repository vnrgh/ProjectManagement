//package example.spring.service;
//
//import example.spring.model.dto.SignInRequestDTO;
//import example.spring.model.dto.SignInResponseDTO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class AuthServiceTest {
//    @Autowired
//    private AuthService authService;
//
////    @Test
////    void signInTest() {
////        SignInRequestDTO signInRequestDTO = new SignInRequestDTO("admin", "pass");
////        SignInResponseDTO signInResponseDTO = authService.signin(signInRequestDTO);
////        assertNotNull(signInResponseDTO.getAccessToken());
////    }
//
////    @Test
////    void signInExceptionTest() {
////        SignInRequestDTO signInRequestDTO = new SignInRequestDTO("invalidUser", "wrongPassword");
////        assertThrows(BadCredentialsException.class, () -> authService.signin(signInRequestDTO) , "Invalid username or password");
////    }
//}
