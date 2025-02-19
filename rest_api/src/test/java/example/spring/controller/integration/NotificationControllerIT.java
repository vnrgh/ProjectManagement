package example.spring.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.spring.config.TestContainerConfig;
import example.spring.kafka.event.NotificationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestContainerConfig.class)
class NotificationControllerIT {
    private static final String TOPIC_NAME = "customTopic";

    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.1"))
            .withExposedPorts(9093);

    @Autowired
    private MockMvc mockMvc;

    private KafkaConsumer<String, Object> consumer;

    @DynamicPropertySource
    static void configureKafka(DynamicPropertyRegistry registry) {
        kafkaContainer.start();
        System.out.println("Kafka started at: " + kafkaContainer.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @BeforeEach
    void setUpConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));
    }

    @Test
    void testSendMessage() throws Exception {
        NotificationEvent event = new NotificationEvent("mail@mail.com", "message");

        mockMvc.perform(post("/api/notify/mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(content().string("message: \n" + event.getMessage() + "\nsent successfully"));
    }

    @AfterAll
    static void stopKafka() {
        kafkaContainer.stop();
    }
}