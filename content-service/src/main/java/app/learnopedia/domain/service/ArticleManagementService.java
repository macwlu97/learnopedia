package app.learnopedia.domain.service;

import app.learnopedia.domain.model.Article;
import app.learnopedia.infrastructure.repository.primary.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleManagementService {
    private final ArticleRepository repository;

    public ArticleManagementService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        article.setVersion(1);
        return repository.save(article);
    }

    public Optional<Article> getArticle(UUID id) {
        return repository.findById(id);
    }
}

