//package example.spring.listener;
//
//import example.spring.service.CustomMailSender;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.time.Duration;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.timeout;
//import static org.springframework.test.web.client.ExpectedCount.times;
//import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
//
//@EmbeddedKafka(partitions = 1, topics = {"welcomeMailTopic"})
//@SpringBootTest(properties = {
//        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
//        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}",
//        "spring.kafka.consumer.group-id=notificationTest",
//        "spring.kafka.consumer.auto-offset-reset=earliest"
//})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles("test")
//class WelcomeMailTopicListenerTest {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @SpyBean
//    private WelcomeMailTopicListener welcomeMailTopicListener;
//
//    @MockBean
//    private CustomMailSender mailSender;
//
//    @Captor
//    private ArgumentCaptor<String> emailCaptor;
//
//    @Captor
//    private ArgumentCaptor<String> messageCaptor;
//
//    @Autowired
//    private EmbeddedKafkaBroker embeddedKafkaBroker;
//
//    private static final String TOPIC = "welcomeMailTopic";
//
//    @Test
//    void shouldReceiveKafkaMessageAndSendMail() {
//        // Данные для теста
//        String email = "test@example.com";
//        String message = "Welcome, user!";
//        String data = email + ";" + message;
//
//        System.out.println("🚀 Отправляем сообщение в Kafka: " + data);
//
//        kafkaTemplate.send(TOPIC, data);
//        kafkaTemplate.flush();
//
//        System.out.println("✅ Сообщение отправлено в Kafka");
//
//        // Ожидаем, что листенер получит сообщение
//        await().atMost(Duration.ofSeconds(5))
//                .untilAsserted(() -> Mockito.verify(welcomeMailTopicListener, Mockito.times(1))
//                        .listener(anyString()));
//
//        System.out.println("✅ Листенер вызван");
//
//        // Проверяем, что mailSender отправил письмо
//        Mockito.verify(mailSender, timeout(5000).times(1))
//                .sendMail(emailCaptor.capture(), messageCaptor.capture());
//
//        // Проверяем, что переданные параметры правильные
//        assertEquals(email, emailCaptor.getValue());
//        assertEquals(message, messageCaptor.getValue());
//    }
//}
//
