package example.spring.controller;

import example.spring.kafka.MessageProducer;
import example.spring.kafka.event.NotificationEvent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notify")
public class NotificationController {
    private final MessageProducer messageProducer;

    public NotificationController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }


    @PostMapping("/mail")
    public ResponseEntity<String> signup(@Valid @RequestBody NotificationEvent event) {
        messageProducer.sendJsonMessage("customTopic", event);
        return new ResponseEntity<>("message: \n" + event.getMessage() + "\nsent successfully", HttpStatus.OK);
    }
}