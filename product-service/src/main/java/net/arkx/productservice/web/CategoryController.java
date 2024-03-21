package net.arkx.productservice.web;

import jakarta.persistence.EntityNotFoundException;
import net.arkx.productservice.entities.Category;
import net.arkx.productservice.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    //***********************************DEPENDENCY INJECTION*************************************************
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //***********************************POST METHOD******************************************************
    @PostMapping("/create")
    public void addCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
    }

    //***********************************GET METHOD******************************************************
    @GetMapping
    public List<Category> getAllCaategories() {
        return categoryService.getCategory();
    }
    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.searchCategoryById(id);
    }
    @GetMapping("/{name}")
    public Category getCategoryByName(@RequestParam String name){
        return categoryService.searchCategoryByName(name);
    }

    //***********************************DELETE METHOD******************************************************
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }
    @DeleteMapping
    public void deleteAllCategories(){
        categoryService.deleteAllCategory();
    }

    //***********************************UPDATE METHOD******************************************************
    @PutMapping("{id}")
    public void updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    )
        throws EntityNotFoundException{
        categoryService.updateCategory(id,category);
    }

}


