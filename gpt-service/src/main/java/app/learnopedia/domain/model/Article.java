package app.learnopedia.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class Article {

    private UUID id;

    private String title;

    private String content;

    private String source;

    private int version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
