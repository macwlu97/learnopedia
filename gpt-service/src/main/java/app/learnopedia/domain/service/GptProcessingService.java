package app.learnopedia.domain.service;

import app.learnopedia.infrastructure.gptclient.GptClient;
import org.springframework.stereotype.Service;

@Service
public class GptProcessingService {

    private final GptClient gptRestClient;

    public GptProcessingService(GptClient gptRestClient) {
        this.gptRestClient = gptRestClient;
    }

    public String askGpt(String prompt) {
        return gptRestClient.generate(prompt);
    }
}
