package example.spring.kafka;

import example.spring.kafka.event.NotificationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class MessageProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTemplate<String, String> stringKafkaTemplate;

    public MessageProducer(KafkaTemplate<String, Object> kafkaTemplate, KafkaTemplate<String, String> stringKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.stringKafkaTemplate = stringKafkaTemplate;
    }

    public void sendJsonMessage(String topic, NotificationEvent event) {
        kafkaTemplate.send(topic, event);
    }

    public void sendStringMessage(String topic, String message) {
        stringKafkaTemplate.send(topic, message);
    }
}
