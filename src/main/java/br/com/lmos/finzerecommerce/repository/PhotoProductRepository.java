package br.com.lmos.finzerecommerce.repository;

import br.com.lmos.finzerecommerce.entity.PhotoProduct;
import br.com.lmos.finzerecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhotoProductRepository extends JpaRepository<PhotoProduct, Long> {

    Optional<PhotoProduct> findByPublicId(UUID publicId);

    List<PhotoProduct> findByProduct(Product product);

    List<PhotoProduct> findByProductOrderByDisplayOrderAsc(Product product);

    void deleteByProduct(Product product);
}
