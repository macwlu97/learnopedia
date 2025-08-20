package app.learnopedia.infrastructure.restclient;

import app.learnopedia.domain.model.Article;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ContentServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/articles";

    public Map<String, Object> getArticleById(String articleId) {
        try {
            return restTemplate.getForObject(baseUrl + "/" + articleId, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

//    public Article getArticleById(String articleId) {
//        try {
//            return restTemplate.getForObject(baseUrl + "/" + articleId, Article.class);
//        } catch (Exception e) {
//            System.err.println("Error fetching article: " + e.getMessage());
//            return null;
//        }
//    }
}
