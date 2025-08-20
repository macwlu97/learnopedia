package app.learnopedia.domain.service;

import app.learnopedia.infrastructure.client.GptRestClient;
import org.springframework.stereotype.Service;

@Service
public class GptClientService {

    private final GptRestClient gptRestClient;

    public GptClientService(GptRestClient gptRestClient) {
        this.gptRestClient = gptRestClient;
    }

    public String askGpt(String prompt) {
        return gptRestClient.generate(prompt);
    }
}
