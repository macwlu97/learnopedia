package app.learnopedia.domain.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GptPublisherService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "articles-to-gpt";

    public GptPublisherService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishArticleId(String articleId) {
        kafkaTemplate.send(topic, articleId);
    }
}
