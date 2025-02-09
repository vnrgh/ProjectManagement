package example.spring.config;

import example.spring.kafka.MessageProducer;
import example.spring.security.jwt.TokenProvider;
import example.spring.service.AuthService;
import example.spring.service.EmployeeService;
import example.spring.service.ProjectService;
import example.spring.service.TaskService;
import example.spring.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class MockConfig {
    @Bean
    public AuthService authService() {
        return mock(AuthService.class);
    }

    @Bean
    public MessageProducer messageProducer() {
        return mock(MessageProducer.class);
    }

    @Bean
    public EmployeeService employeeService() {
        return mock(EmployeeService.class);
    }

    @Bean
    public KafkaTemplate<String, String> template() {
        return mock(KafkaTemplate.class);
    }

    @Bean
    public ProjectService projectService() {
        return mock(ProjectService.class);
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    public TaskService taskService() {
        return mock(TaskService.class);
    }

    @Bean
    public TokenProvider tokenProvider() {
        return mock(TokenProvider.class);
    }
}
