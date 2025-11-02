package br.com.lmos.finzerecommerce.repository;

import br.com.lmos.finzerecommerce.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
