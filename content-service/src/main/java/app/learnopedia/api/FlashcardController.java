package app.learnopedia.api;

import app.learnopedia.domain.model.Flashcard;
import app.learnopedia.domain.model.UserProgress;
import app.learnopedia.domain.service.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public Flashcard createFlashcard(@RequestBody Flashcard flashcard) {
        return flashcardService.createFlashcard(flashcard);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcard(@PathVariable UUID id) {
        return flashcardService.getFlashcard(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashcards();
    }

    @PostMapping("/{id}/progress")
    public UserProgress addProgress(@PathVariable UUID id,
                                    @RequestParam String userId,
                                    @RequestParam boolean correct) {
        return flashcardService.addUserProgress(userId, id, correct);
    }

    @GetMapping("/progress/{userId}")
    public List<UserProgress> getUserProgress(@PathVariable String userId) {
        return flashcardService.getUserProgress(userId);
    }
}
