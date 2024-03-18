package net.arkx.productservice.service;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final net.arkx.productservice.repository.CategoryRepository categoryRepository;

    public CategoryService(net.arkx.productservice.repository.CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
