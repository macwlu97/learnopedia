package app.learnopedia.domain.service;

import app.learnopedia.domain.model.Flashcard;
import app.learnopedia.domain.model.UserProgress;
import app.learnopedia.infrastructure.repository.primary.FlashcardRepository;
import app.learnopedia.infrastructure.repository.primary.UserProgressRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final UserProgressRepository userProgressRepository;

    public FlashcardService(FlashcardRepository flashcardRepository,
                            UserProgressRepository userProgressRepository) {
        this.flashcardRepository = flashcardRepository;
        this.userProgressRepository = userProgressRepository;
    }

    // CRUD Flashcards
    public Flashcard createFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public Optional<Flashcard> getFlashcard(UUID id) {
        return flashcardRepository.findById(id);
    }

    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    // User progress
    public UserProgress addUserProgress(String userId, UUID flashcardId, boolean correct) {
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));

        UserProgress progress = new UserProgress();
        progress.setUserId(userId);
        progress.setFlashcard(flashcard);
        progress.setCorrect(correct);
        progress.setAttempts(correct ? 1 : 1);
        progress.setLastAttemptAt(LocalDateTime.now());

        return userProgressRepository.save(progress);
    }

    public List<UserProgress> getUserProgress(String userId) {
        return userProgressRepository.findByUserId(userId);
    }
}
