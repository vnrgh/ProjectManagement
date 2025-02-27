package example.spring.listener;

import example.spring.service.CustomMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WelcomeMailTopicListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeMailTopicListener.class);
    private final CustomMailSender mailSender;

    public WelcomeMailTopicListener(CustomMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.welcomeMailTopic}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"spring.json.value.default.type=java.lang.String"}
    )
    public void listener(String data) {
        LOGGER.info("Listener received: {}", data);

        String[] parts = data.split(";", 2);
        if (parts.length < 2) {
            LOGGER.error("Invalid message format: {}", data);
            return;
        }

        String email = parts[0];
        String message = parts[1];
        mailSender.sendMail(email, message);
    }
}
