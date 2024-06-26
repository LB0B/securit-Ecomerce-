package net.arkx.userservice.repository;


import net.arkx.userservice.entities.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRespository  extends JpaRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);
}
