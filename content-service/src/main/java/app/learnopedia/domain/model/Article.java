package app.learnopedia.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

// Article.java
@Entity
@Data
public class Article {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    @Lob
    private String content;
    private String source;
    private int version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private Category category;

    @ManyToMany
    private Set<Tag> tags;
}
