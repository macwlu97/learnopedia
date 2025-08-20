package app.learnopedia.api;

import app.learnopedia.domain.service.GptProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gpt")
public class GptController {

    private final GptProcessingService gptService;

    public GptController(GptProcessingService gptService) {
        this.gptService = gptService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askGpt(@RequestParam String prompt) {
        String answer = gptService.askGpt(prompt);
        return ResponseEntity.ok(answer);
    }
}
