package app.learnopedia.api;

import app.learnopedia.domain.model.Article;
import app.learnopedia.domain.service.ArticleManagementService;
import app.learnopedia.domain.service.FetchArticleFromWikipediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleManagementService managementService;
    private final FetchArticleFromWikipediaService fetchService;

    public ArticleController(ArticleManagementService managementService,
                             FetchArticleFromWikipediaService fetchService) {
        this.managementService = managementService;
        this.fetchService = fetchService;
    }

    @PostMapping("/import")
    public Article importFromWikipedia(@RequestParam String title) {
        return fetchService.fetchAndSave(title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable UUID id) {
        return managementService.getArticle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
