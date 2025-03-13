package example.spring.listener;

import example.spring.kafka.event.NotificationEvent;
import example.spring.service.CustomMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CustomTopicListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTopicListener.class);

    private final CustomMailSender mailSender;

    public CustomTopicListener(CustomMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.customTopic}", groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listener(@Payload NotificationEvent event) {
        LOGGER.info("Received a message contains a notification information with receiver {} and message: {}",
                event.getNotificationReceiver(), event.getMessage());
        mailSender.sendMail(event.getNotificationReceiver(), event.getMessage());
    }
}