package br.com.lmos.finzerecommerce.repository;

import br.com.lmos.finzerecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByPublicId(UUID publicId);
}
