package app.learnopedia.infrastructure.repository.primary;

import app.learnopedia.domain.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    List<Article> findByTitleContainingIgnoreCase(String title);

    Optional<Article> findByTitle(String title);
}
