package app.learnopedia.domain.service;

import app.learnopedia.domain.model.Article;
import app.learnopedia.infrastructure.repository.primary.ArticleRepository;
import app.learnopedia.infrastructure.wikipedia.WikipediaClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class FetchArticleFromWikipediaService {

    private final ObjectMapper objectMapper;
    private final WikipediaClient wikipediaClient;
    private final ArticleRepository repository;
    private final GptPublisherService gptPublisherService;

    public FetchArticleFromWikipediaService(ObjectMapper objectMapper, WikipediaClient wikipediaClient, ArticleRepository repository, GptPublisherService gptPublisherService) {
        this.objectMapper = objectMapper;
        this.wikipediaClient = wikipediaClient;
        this.repository = repository;
        this.gptPublisherService = gptPublisherService;
    }

    @Transactional
    public Article fetchAndSave(String title) {
        return repository.findByTitle(title)
                .orElseGet(() -> {
                    String content = wikipediaClient.fetchArticle(title);
                    Article article = new Article();
                    article.setTitle(title);
                    article.setContent(content);
                    article.setSource("wikipedia");
                    article.setVersion(1);
                    article.setCreatedAt(LocalDateTime.now());
                    article.setUpdatedAt(LocalDateTime.now());
                    Article savedArticle = repository.save(article);

                    // konwersja artykułu do JSON
                    String articleJson = convertArticleToJson(savedArticle);

                    // publikacja ID artykułu do GPT Service przez Kafka
                    gptPublisherService.publishArticleId(savedArticle.getId().toString());

                    log.info("Publishing article ID {} to Kafka", savedArticle.getId());

                    return savedArticle;
                });
    }

    private String convertArticleToJson(Article article) {
        try {
            return objectMapper.writeValueAsString(article);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to convert article to JSON", e);
        }
    }
}
