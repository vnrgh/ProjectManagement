package example.spring.controller.integration;

import example.spring.config.TestContainerConfig;
import example.spring.model.User;
import example.spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserByIdTest() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "username", "password", "test@example.com", null, null);
        User savedUser = userRepository.save(user);

        mockMvc.perform(get("/api/user/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getAllUsersTest() throws Exception {
        User user1 = new User(1L, "username1", "password1", "test1@example.com", null, null);
        User user2 = new User(2L, "username2", "password2", "test2@example.com", null, null);

        userRepository.saveAll(List.of(user1, user2));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}











