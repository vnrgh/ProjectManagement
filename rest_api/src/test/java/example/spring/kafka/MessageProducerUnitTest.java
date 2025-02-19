package example.spring.kafka;

import example.spring.kafka.event.NotificationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class MessageProducerUnitTest {
    private MessageProducer messageProducer;
    private KafkaTemplate<String, String> stringKafkaTemplate;
    private KafkaTemplate<String, Object> objectKafkaTemplate;

    @BeforeEach
    void setUp() {
        stringKafkaTemplate = mock(KafkaTemplate.class);
        objectKafkaTemplate = mock(KafkaTemplate.class);
        messageProducer = new MessageProducer(objectKafkaTemplate, stringKafkaTemplate);
    }

    @Test
    void sendStringMessageTest() {
        String topicName = "test";
        String message = "Hello";

        messageProducer.sendStringMessage(topicName, message);

        verify(stringKafkaTemplate, times(1)).send(topicName, message);
    }

    @Test
    void sendJsonMessageTest() {
        NotificationEvent event = new NotificationEvent("test", "Hello");

        messageProducer.sendJsonMessage(event.getNotificationReceiver(), event);

        verify(objectKafkaTemplate, times(1)).send(event.getNotificationReceiver(), event);
    }

}
