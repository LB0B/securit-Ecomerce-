package net.arkx.productservice.web;

import net.arkx.productservice.service.SubCategoryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }
}
