package example.spring.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("!test")
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.welcomeMailTopic}")
    private String welcomeMailTopic;

    @Value("${spring.kafka.topic.testTopic}")
    private String testTopic;

    @Bean
    public NewTopic testTopic() {
        return TopicBuilder
                .name(testTopic)
                .build();
    }

    @Bean
    public NewTopic welcomeMailTopic() {
        return TopicBuilder
                .name(welcomeMailTopic)
                .build();
    }
}