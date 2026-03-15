package ap2._15.keller.rejas.rest;

import ap2._15.keller.rejas.model.Product;
import ap2._15.keller.rejas.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productService.create(product)
                .map(savedProduct -> ResponseEntity.status(HttpStatus.CREATED).body(savedProduct));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        return productService.update(id, product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/deactivate")
    public Mono<ResponseEntity<Product>> deactivateProduct(@PathVariable Long id) {
        return productService.deactivate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/activate")
    public Mono<ResponseEntity<Product>> activateProduct(@PathVariable Long id) {
        return productService.activate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long id) {
        return productService.findById(id)
                .flatMap(existing -> productService.delete(id)
                        .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public Flux<Product> getProductsByCategory(@PathVariable String category) {
        return productService.findByCategory(category);
    }

    @GetMapping("/active")
    public Flux<Product> getActiveProducts() {
        return productService.findActive();
    }

    @GetMapping("/sku/{sku}")
    public Mono<ResponseEntity<Product>> getProductBySku(@PathVariable String sku) {
        return productService.findBySku(sku)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public Flux<Product> searchProducts(@RequestParam String name) {
        return productService.searchByName(name);
    }
}
