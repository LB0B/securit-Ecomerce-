package net.arkx.productservice.service;

import lombok.Getter;
import net.arkx.productservice.entities.Category;
import net.arkx.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CategoryService {
    //private final net.arkx.productservice.repository.CategoryRepository categoryRepository;
    //public CategoryService(net.arkx.productservice.repository.CategoryRepository categoryRepository) {
       //this.categoryRepository = categoryRepository;

//***********************************DEPENDENCY INJECTION*************************************************
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
//***************************************CRUD METHODS**********************************************
//CREATE METHOD:
   public void createCategory(Category category){
        categoryRepository.save(category);
   }
//RETRIEVE METHOD:
    public List<Category> getCategory(){
       return categoryRepository.findAll();
    }
//DELETE METHOD:
    public void deleteCategoryById(Long id){
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }
    }
//UPDATE METHOD:
    public void updateCategory(Long id,Category category){
        if(categoryRepository.findById(id).isPresent()) {
            Category categoryToUpdate = categoryRepository.findById(id).get();
            categoryToUpdate.setName(category.getName());
            categoryRepository.save(categoryToUpdate);
        }
    }
//***************************************SEARCH METHODS**********************************************
//SEARCH BY ID:
    public Category searchCategoryById(Long id){
        return categoryRepository.findById(id).get();
    }
//SEARCH BY NAME:
    //Category catToSearch;
//    public Category searchCategoryByName(String nameToSearch){
//        List<Category> categoryList=getCategory();
//        for(Category category:categoryList){
//            if(category.getName().equalsIgnoreCase(nameToSearch)){
//                Long id=category.getId();
//                catToSearch=searchCategoryById(id);
//            }
//        }
//        return catToSearch;
//    }
    public Category searchCategoryByName(String name){
        return categoryRepository.findByNameIgnoreCase(name);
    }
}

