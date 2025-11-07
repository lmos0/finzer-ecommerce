package br.com.lmos.finzerecommerce.repository;

import br.com.lmos.finzerecommerce.entity.PhotoProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoProductRepository extends JpaRepository<PhotoProduct, Long> {
}
