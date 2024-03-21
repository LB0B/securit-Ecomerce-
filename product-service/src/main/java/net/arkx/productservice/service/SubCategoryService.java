package net.arkx.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import net.arkx.productservice.entities.Category;
import net.arkx.productservice.entities.SubCategory;
import net.arkx.productservice.repository.CategoryRepository;
import net.arkx.productservice.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Name;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }
    //List SubCategory
    public List<SubCategory> getAll()
    {
        return subCategoryRepository.findAll();
    }

    //Add SubCategory

    public void AddSubCategory(SubCategory subCategory)
    {
        subCategoryRepository.save(subCategory);
    }

    //Delete SubCategory

    public void DeleteSubCategory(long id)
    {
        if (subCategoryRepository.existsById(id))
        {
            subCategoryRepository.deleteById(id);
        }

    }


    //Delete All categories
    public void  DeleteAllSubCategories() {
        subCategoryRepository.deleteAll();
    }

    //Update SubCategory
    public void UpdateSubCategory(long id ,SubCategory subCategory)
    {
        if (subCategoryRepository.findById(id).isPresent())
        {
            SubCategory subCategoryToUpdate=subCategoryRepository.findById(id).get();
            subCategoryToUpdate.setName(subCategory.getName());
            subCategoryRepository.save(subCategory);
        }
    }


//search by id
    public  SubCategory SearchSubCategoryById(long id) {

        if (subCategoryRepository.findById(id).isPresent())
        {
            return subCategoryRepository.findById(id).get();
        }
        else throw new EntityNotFoundException();
    }

//get SubCategories By Name

    public SubCategory getSubCategoryByName(String name) {
      return subCategoryRepository.findByNameIgnoreCase(name);
    }









}
