//package example.spring.listener;
//
//import example.spring.event.NotificationEvent;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.timeout;
//import static org.mockito.Mockito.verify;
//
//@EmbeddedKafka
//@SpringBootTest(properties =
//        {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
//                "spring.kafka.consumer.bootstrap-servers = ${spring.embedded.kafka.brokers}"})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles("test")
//class CustomMailTopicListenerTest {
//    @Value("${spring.kafka.topic.customTopic}")
//    private String topicName;
//
//    private Producer<String, NotificationEvent> producer;
//
//    @Autowired
//    private EmbeddedKafkaBroker embeddedKafkaBroker;
//
//    @SpyBean
//    private CustomTopicListener customTopicListener;
//
//    @Captor
//    ArgumentCaptor<NotificationEvent> argumentCaptor;
//
//    @BeforeAll
//    void setUp() {
//        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
//        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer<NotificationEvent>()).createProducer();
//    }
//
//    @Test
//    void testLogKafkaMessage() {
//        NotificationEvent event = new NotificationEvent("mail@mail.com", "message");
//        producer.send(new ProducerRecord<>(topicName, event));
//
//        verify(customTopicListener, timeout(5000).times(1))
//                .consumeNotificationEvents(argumentCaptor.capture());
//
//        NotificationEvent notificationEvent = argumentCaptor.getValue();
//
//        assertNotNull(notificationEvent);
//        assertEquals("mail@mail.com", notificationEvent.getNotificationReceiver());
//        assertEquals("message", notificationEvent.getMessage());
//    }
//
//    @AfterAll
//    void shutdown() {
//        producer.close();
//    }
//}
//
//
//
//
//
//
//
