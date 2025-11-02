package br.com.lmos.finzerecommerce.repository;

import br.com.lmos.finzerecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPublicId(UUID publicId);
    Optional<Customer> findByEmail(String email);
    boolean existsByCpf(String cpf);
}
