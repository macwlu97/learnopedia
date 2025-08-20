package app.learnopedia.domain.service;

import app.learnopedia.infrastructure.restclient.ContentServiceClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GptConsumerService {

    private final ContentServiceClient contentClient;
    private final GptProcessingService gptService;

    public GptConsumerService(ContentServiceClient contentClient, GptProcessingService gptService) {
        this.contentClient = contentClient;
        this.gptService = gptService;
    }

    @KafkaListener(topics = "articles-to-gpt", groupId = "gpt-group")
    public void consume(String articleId) {
        // pobierz artykuł z content-service
        var article = contentClient.getArticleById(articleId);
        if (article != null) {

            // Przetwarzanie artykułu
            var educationalContent = gptService.generateEducationalContent(articleId);

            // Tu możesz np. zapisać wynik do bazy lub opublikować do kolejnego topicu
            System.out.println("Generated educational content for article " + articleId + ": " + educationalContent);


//             przetwórz artykuł przez GPT
//            String gptResponse = gptService.askGpt(article.getContent());
//            System.out.println("GPT Response for article " + articleId + ": " + gptResponse);
        }
    }
}
