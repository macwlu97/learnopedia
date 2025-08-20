package app.learnopedia.infrastructure.restclient;

import app.learnopedia.domain.model.Article;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContentServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/articles";

    public Article getArticleById(String articleId) {
        try {
            return restTemplate.getForObject(baseUrl + "/" + articleId, Article.class);
        } catch (Exception e) {
            System.err.println("Error fetching article: " + e.getMessage());
            return null;
        }
    }
}
