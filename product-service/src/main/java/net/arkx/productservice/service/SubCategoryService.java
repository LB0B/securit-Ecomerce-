package net.arkx.productservice.service;

import net.arkx.productservice.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }
}
