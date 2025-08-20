package app.learnopedia.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID articleId;  // FK do artykułu

    private String question;
    private String answer;

    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL)
    private Set<UserProgress> progress;
}
