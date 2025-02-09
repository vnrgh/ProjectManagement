package example.spring.controller.integration;

import example.spring.config.TestContainerConfig;
import example.spring.kafka.MessageProducer;
import example.spring.model.dto.SignInRequestDTO;
import example.spring.model.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageProducer messageProducer;

    @Test
    void signUpTest() throws Exception {
        UserRequestDTO userDTO = new UserRequestDTO("test_name123", "test_password123","test123@mail.com");

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void signInTest() throws Exception {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO("admin", "pass");

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signInRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString());
    }

    @Test
    void signInThrowsExceptionTest() throws Exception {
        SignInRequestDTO requestDTO = new SignInRequestDTO("invalidUsername", "wrongPassword");

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username or password"));
    }
}


