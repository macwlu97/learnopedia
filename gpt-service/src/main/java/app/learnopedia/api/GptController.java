package app.learnopedia.api;

import app.learnopedia.domain.service.GptClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gpt")
public class GptController {

    private final GptClientService gptClientService;

    public GptController(GptClientService gptClientService) {
        this.gptClientService = gptClientService;
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String prompt) {
        return gptClientService.askGpt(prompt);
    }
}
