package app.learnopedia.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

import java.util.UUID;

// Category.java
@Entity
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
}
