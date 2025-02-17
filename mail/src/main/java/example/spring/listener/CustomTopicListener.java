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

    Logger logger = LoggerFactory.getLogger(CustomTopicListener.class);
    private final CustomMailSender mailSender;

    public CustomTopicListener(CustomMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "customTopic"
    )
    public void listener(@Payload NotificationEvent event) {
        logger.info("Received a message contains a notification information with receiver {} and message: {}",
                event.getNotificationReceiver(), event.getMessage());
        mailSender.sendMail(event.getNotificationReceiver(), event.getMessage());
    }
}