package example.spring.listener;

import example.spring.service.CustomMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestTopicListener {

    Logger logger = LoggerFactory.getLogger(TestTopicListener.class);
    private final CustomMailSender mailSender;

    public TestTopicListener(CustomMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "testTopic"
    )
    public void listener(String data) {
        logger.info("Listener received: {}", data);
        mailSender.sendMail("myowndn@gmail.com", data);
    }
    //delete my mail everywhere
}