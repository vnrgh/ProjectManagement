package example.spring;

import example.spring.kafka.MessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@MockBean(MessageProducer.class)
class ProjectManagementApplicationTest {

    @Test
    void loadContext() {
        //Test for loading Application context
    }
}
