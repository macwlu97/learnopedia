package app.learnopedia.infrastructure.gptclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GptClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8000/generate";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generate(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // budujemy poprawny JSON
            Map<String, String> body = new HashMap<>();
            body.put("text", prompt);

            String jsonBody = objectMapper.writeValueAsString(body);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("response");
            } else {
                throw new RuntimeException("GPT service returned error: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to call GPT service", e);
        }
    }
}
