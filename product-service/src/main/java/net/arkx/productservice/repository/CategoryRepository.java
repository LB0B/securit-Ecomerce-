package net.arkx.productservice.repository;

import net.arkx.productservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
     Category findByNameIgnoreCase(String name);
}
