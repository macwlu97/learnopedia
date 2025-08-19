package app.learnopedia.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

import java.util.UUID;

// Tag.java
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
}