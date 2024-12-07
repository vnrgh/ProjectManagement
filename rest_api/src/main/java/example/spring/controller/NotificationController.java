package example.spring.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notify")
public class NotificationController {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/mail")
    public ResponseEntity<String> signup(@Valid @RequestBody String message) {
        kafkaTemplate.send("testTopic", message);
        return new ResponseEntity<>("message: \n" + message + "\n sent successfully", HttpStatus.OK);
    }
}
