package app.learnopedia.infrastructure.wikipedia;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class WikipediaClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchArticle(String title) {
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + title;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (String) response.get("extract");
    }
}
