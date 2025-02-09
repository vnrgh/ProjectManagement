package example.spring.exception;

import example.spring.config.MockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({EntityExceptionHandler.class, MockConfig.class})
class EntityExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void handleAllExceptionsTest() throws Exception {
        mockMvc.perform(get("/throw-generic"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unexpected error occurred"));
    }

    @Test
    void handleEmployeeNotFoundExceptionTest() throws Exception {
        mockMvc.perform(get("/throw-employee-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee not found"));
    }

    @Test
    void handleProjectNotFoundExceptionTest() throws Exception {
        mockMvc.perform(get("/throw-project-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Project not found"));
    }

    @Test
    void handleTaskNotFoundExceptionTest() throws Exception {
        mockMvc.perform(get("/throw-task-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found"));
    }

    @Test
    void handleUserNotFoundExceptionTest() throws Exception {
        mockMvc.perform(get("/throw-user-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}
