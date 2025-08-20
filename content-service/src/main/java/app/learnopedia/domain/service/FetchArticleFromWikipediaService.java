package app.learnopedia.domain.service;

import app.learnopedia.domain.model.Article;
import app.learnopedia.infrastructure.repository.primary.ArticleRepository;
import app.learnopedia.infrastructure.wikipedia.WikipediaClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FetchArticleFromWikipediaService {
    private final WikipediaClient wikipediaClient;
    private final ArticleRepository repository;
    private final GptPublisherService gptPublisherService;

    public FetchArticleFromWikipediaService(WikipediaClient wikipediaClient, ArticleRepository repository, GptPublisherService gptPublisherService) {
        this.wikipediaClient = wikipediaClient;
        this.repository = repository;
        this.gptPublisherService = gptPublisherService;
    }

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

                    return savedArticle;
                });
    }

    private String convertArticleToJson(Article article) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(article);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to convert article to JSON", e);
        }
    }
}
