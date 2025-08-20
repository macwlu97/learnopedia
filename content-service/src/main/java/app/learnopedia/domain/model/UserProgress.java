package app.learnopedia.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String userId;      // np. UUID użytkownika
    private boolean correct;    // czy odpowiedział poprawnie
    private int attempts;       // ile prób

    @ManyToOne
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    private LocalDateTime lastAttemptAt;
}
