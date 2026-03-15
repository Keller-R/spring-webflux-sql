package ap2._15.keller.rejas.service;

import ap2._15.keller.rejas.model.Product;
import ap2._15.keller.rejas.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Mono<Product> create(Product product) {
        product.setId(null);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setIsActive(true);
        return productRepository.save(product);
    }

    public Mono<Product> update(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setSku(product.getSku());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setStockQuantity(product.getStockQuantity());
                    existingProduct.setIsActive(product.getIsActive());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Product> deactivate(Long id) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setIsActive(false);
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Product> activate(Long id) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setIsActive(true);
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Void> delete(Long id) {
        return productRepository.deleteById(id);
    }

    public Flux<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Flux<Product> findActive() {
        return productRepository.findByIsActiveTrue();
    }

    public Mono<Product> findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public Flux<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
