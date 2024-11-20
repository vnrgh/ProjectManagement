//package example.spring.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import example.spring.exception.EntityExceptionHandler;
//import example.spring.model.dto.SignInRequestDTO;
//import example.spring.model.dto.SignInResponseDTO;
//import example.spring.security.jwt.TokenProvider;
//import example.spring.service.AuthService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertInstanceOf;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//
//@WebMvcTest(AuthControllerTest.class)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class AuthControllerTest {
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthService authService;
//
//    @MockBean
//    private TokenProvider tokenProvider;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService))
//                .setControllerAdvice(new EntityExceptionHandler()).build();
//    }
//
//    @Test
//    void signInTest() throws Exception {
//        SignInRequestDTO signInRequestDTO = new SignInRequestDTO("admin", "password");
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonBody = mapper.writeValueAsString(signInRequestDTO);
//        when(authService.signin(signInRequestDTO)).thenReturn(new SignInResponseDTO("test_token"));
//
//        mockMvc.perform(post("/auth/signin").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(),
//                        "{\"accessToken\":\"test_token\"}"
//                        ))
//                .andExpect(result -> { assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
//                })
//                .andReturn();
//    }
//
//    @Test
//    void signInExceptionTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        SignInRequestDTO signInRequestDTO = new SignInRequestDTO("invalidUser", "wrongPassword");
//
//        String jsonBody = mapper.writeValueAsString(signInRequestDTO);
//        when(authService.signin(signInRequestDTO)).thenThrow(new BadCredentialsException("Invalid username or password"));
//        mockMvc.perform(post("/auth/signin").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(result -> {
//                    assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
//                    assertInstanceOf(BadCredentialsException.class, result.getResolvedException());
//                    assertEquals("Invalid username or password", result.getResolvedException().getMessage());
//                })
//                .andReturn();
//    }
//}
