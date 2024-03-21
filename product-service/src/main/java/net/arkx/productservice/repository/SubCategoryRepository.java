package net.arkx.productservice.repository;

import net.arkx.productservice.entities.Category;
import net.arkx.productservice.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findByCategory(Category category);
    SubCategory getCategoryByName(String name);
    SubCategory findByNameIgnoreCase(String name);
}
