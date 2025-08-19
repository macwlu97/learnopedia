package app.learnopedia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
class HelloWorldEndpoint {

    @GetMapping("/{id}")
    ResponseEntity<String> getProduct(@PathVariable String id) {
        // Możesz tutaj użyć id do logiki aplikacji, np. szukać produktu w bazie danych
        return ResponseEntity.ok("Hellooo, Product ID: " + id);
    }
}
