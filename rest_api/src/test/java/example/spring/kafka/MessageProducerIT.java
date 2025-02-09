//package example.spring.kafka;
//
//import example.spring.config.TestContainerConfig;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//@Testcontainers
//@SpringBootTest
////@SpringBootTest(properties =
////        {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
////                "spring.kafka.producer.bootstrap-servers = ${spring.embedded.kafka.brokers}",
////                "spring.liquibase.enabled=false"})
////@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles("test")
//@ContextConfiguration(classes = TestContainerConfig.class)
//public class MessageProducerIT {
//    @Container
//    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
//
//    @DynamicPropertySource
//    static void configureKafka(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
//    }
//
//    @Value("${spring.kafka.topic.name}")
//    private String TOPIC_NAME;
//
//    @Autowired
//    private KafkaTemplate<String, String> template;
//    @MockBean
//    private MessageProducer messageProducer;
//
//    @BeforeEach
//    void setUp() {
//        messageProducer = new MessageProducer(template);
//    }
//
//    @Test
//    void sendMessageTest() {
//        String message = "Hello!";
//
//        messageProducer.sendMessage(TOPIC_NAME, message);
//    }
//}
