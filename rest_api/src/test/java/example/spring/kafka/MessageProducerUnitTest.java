//package example.spring.kafka;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import static org.mockito.Mockito.*;
//
//class MessageProducerUnitTest {
//    private MessageProducer messageProducer;
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @BeforeEach
//    void setUp() {
//        kafkaTemplate = mock(KafkaTemplate.class);
//        messageProducer = new MessageProducer(kafkaTemplate);
//    }
//
//    @Test
//    void sendMessageTest() {
//        String topicName = "test";
//        String message = "Hello";
//
//        messageProducer.sendMessage(topicName, message);
//
//        verify(kafkaTemplate, times(1)).send(topicName, message);
//    }
//}
