//package example.spring.kafka;
//
//import example.spring.config.TestContainerConfig;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//
//import static org.junit.Assert.*;
//
//@Testcontainers
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
////        (properties =
////                {"spring.kafka.bootstrap-servers=",
////                        "spring.kafka.consumer.bootstrap-servers="})
//@ActiveProfiles("test")
//class MessageProducerIntegrationTest {
//    private static final KafkaContainer kafkaContainer =
//            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"))
//                    .waitingFor(Wait.forListeningPort());
//
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    private MessageProducer messageProducer;
//
//
//    @BeforeAll
//    static void setUp() {
//        kafkaContainer.start();
//        System.out.println("Kafka started at: " + kafkaContainer.getBootstrapServers());
//    }
//
//
//    @DynamicPropertySource
//    static void configureKafkaProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
//        registry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
//    }
//
//    @AfterAll
//    static void tearDown() {
//        kafkaContainer.stop();
//    }
//
//    @Test
//    void testSendMessage() {
//        String topic = "test-topic";
//        String message = "Hello, Kafka!";
//
//        messageProducer.sendMessage(topic, message);
//
//        Properties props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
//        props.put("group.id", "test-group");
//        props.put("auto.offset.reset", "earliest");
//
//        try (KafkaConsumer<String, String> consumer =
//                     new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer())) {
//            consumer.subscribe(Collections.singletonList(topic));
//            ConsumerRecord<String, String> record = consumer.poll(Duration.ofSeconds(5)).iterator().next();
//            assertEquals(message, record.value());
//        }
//    }
//}
//
//
//
//
