package example.spring.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
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

    @Value("${spring.kafka.topic.customTopic}")
    private String customTopic;

    @Bean
    public NewTopic customTopic() {
        return TopicBuilder
                .name(customTopic)
                .build();
    }

    @Bean
    public NewTopic welcomeMailTopic() {
        return TopicBuilder
                .name(welcomeMailTopic)
                .build();
    }
}