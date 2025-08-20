package app.learnopedia.domain.service;

import app.learnopedia.infrastructure.gptclient.GptClient;
import app.learnopedia.infrastructure.restclient.ContentServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GptProcessingService {

    private final GptClient gptRestClient;
    private final ContentServiceClient contentClient;
    private final RestTemplate restTemplate = new RestTemplate();

    public GptProcessingService(GptClient gptRestClient, ContentServiceClient contentClient) {
        this.gptRestClient = gptRestClient;
        this.contentClient = contentClient;
    }

    public String askGpt(String prompt) {
        return gptRestClient.generate(prompt);
    }

    public Map<String, Object> generateEducationalContent(String articleId) {
        // Pobierz artykuł z content-service
        Map<String, Object> article = contentClient.getArticleById(articleId);
        if (article == null) {
            throw new RuntimeException("Article not found: " + articleId);
        }

        String title = (String) article.get("title");
        String content = (String) article.get("content");

        // Przygotuj prompt w formacie semi-structured
        String prompt = "You are an AI educator. Generate Quizlet-style learning content from this article.\n" +
                "Respond in the following format:\n" +
                "Summary:\n<summary text>\n\n" +
                "Flashcards:\nQ: <question 1>\nA: <answer 1>\nQ: <question 2>\nA: <answer 2>\n...\n\n" +
                "Examples:\n- <example 1>\n- <example 2>\n...\n\n" +
                "Tags:\n- <tag1>\n- <tag2>\n...\n\n" +
                "Title: " + title + "\nContent: " + content;

        // Wywołanie GPT
        String gptResponse = gptRestClient.generate(prompt);

        // Parsowanie semi-structured text na Mapę
        return parseEducationalContent(articleId, title, gptResponse);
    }

    private Map<String, Object> parseEducationalContent(String articleId, String title, String gptText) {
        Map<String, Object> result = new HashMap<>();
        result.put("articleId", articleId);
        result.put("title", title);
        result.put("gptRaw", gptText);

        String[] lines = gptText.split("\n");
        StringBuilder summary = new StringBuilder();
        List<Map<String,String>> flashcards = new ArrayList<>();
        List<String> examples = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        String section = "";
        for(String line : lines) {
            line = line.trim();
            if(line.isEmpty()) continue;

            if(line.startsWith("Summary:")) { section = "summary"; continue; }
            else if(line.startsWith("Flashcards:")) { section = "flashcards"; continue; }
            else if(line.startsWith("Examples:")) { section = "examples"; continue; }
            else if(line.startsWith("Tags:")) { section = "tags"; continue; }

            switch(section) {
                case "summary":
                    summary.append(line).append(" ");
                    break;
                case "flashcards":
                    if(line.startsWith("Q:")) {
                        Map<String, String> card = new HashMap<>();
                        card.put("question", line.substring(3).trim());
                        flashcards.add(card);
                    } else if(line.startsWith("A:") && !flashcards.isEmpty()) {
                        flashcards.get(flashcards.size() - 1).put("answer", line.substring(3).trim());
                    }
                    break;
                case "examples":
                    if(line.startsWith("-")) examples.add(line.substring(1).trim());
                    break;
                case "tags":
                    if(line.startsWith("-")) tags.add(line.substring(1).trim());
                    break;
            }
        }

        result.put("summary", summary.toString().trim());
        result.put("flashcards", flashcards);
        result.put("examples", examples);
        result.put("tags", tags);

        return result;
    }
}
