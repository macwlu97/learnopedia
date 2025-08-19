package app.learnopedia.domain.service;

import app.learnopedia.domain.model.Article;
import app.learnopedia.infrastructure.repository.ArticleRepository;
import app.learnopedia.infrastructure.wikipedia.WikipediaClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FetchArticleFromWikipediaService {
    private final WikipediaClient wikipediaClient;
    private final ArticleRepository repository;

    public FetchArticleFromWikipediaService(WikipediaClient wikipediaClient, ArticleRepository repository) {
        this.wikipediaClient = wikipediaClient;
        this.repository = repository;
    }

    public Article fetchAndSave(String title) {
        String content = wikipediaClient.fetchArticle(title);
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setSource("wikipedia");
        article.setVersion(1);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return repository.save(article);
    }
}
