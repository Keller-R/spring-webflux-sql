package ap2._15.keller.rejas.repository;

import ap2._15.keller.rejas.model.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {

    Flux<Product> findByCategory(String category);

    Flux<Product> findByIsActiveTrue();

    Mono<Product> findBySku(String sku);

    Flux<Product> findByNameContainingIgnoreCase(String name);
}
