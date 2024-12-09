
package example.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CustomMailSender {

    Logger logger = LoggerFactory.getLogger(CustomMailSender.class);

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    public CustomMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //todo поигарться с настройками sendMail
    public void sendMail(String to, String data) {
        try {
            logger.info("Mail preparation started");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setSubject("Project Management Application");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(data);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
