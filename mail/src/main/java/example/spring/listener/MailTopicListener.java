package example.spring.listener;

import example.spring.service.ExampleMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MailTopicListener {

    Logger logger = LoggerFactory.getLogger(MailTopicListener.class);

    private final ExampleMailSender mailSender;

    public MailTopicListener(ExampleMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "testTopic"
    )
    public void listener(String data) {
        logger.info("Listener received: " + data);
        mailSender.sendMail("myowndn@gmail.com", data);
    }
}
