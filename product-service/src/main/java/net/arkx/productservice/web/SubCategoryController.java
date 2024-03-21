package net.arkx.productservice.web;

import jakarta.persistence.EntityNotFoundException;
import net.arkx.productservice.entities.Category;
import net.arkx.productservice.entities.SubCategory;
import net.arkx.productservice.service.SubCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("SubCategories")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }
    @GetMapping
    public List<SubCategory> getAll()
    {
        return subCategoryService.getAll();
    }



    @PostMapping("/create")
    public void AddSubCategory(@RequestBody SubCategory subCategory)
    {
       subCategoryService.AddSubCategory(subCategory);
    }

    @DeleteMapping("{id}")
    public void DeleteSubCategory(@PathVariable long id)
    {
        subCategoryService.DeleteSubCategory(id);
    }

    @DeleteMapping("/deleteall")
    public void DeleteAllSubCategories()
    {
        subCategoryService.DeleteAllSubCategories();
    }
    @PutMapping("{id}")
    public void UpdateSubCategory(
            @PathVariable("id") long id,
            @RequestBody SubCategory subCategory
    )
        throws EntityNotFoundException{
        subCategoryService.UpdateSubCategory(id, subCategory);
    }

    @GetMapping("{id}")
    public SubCategory SearchSubCategoryById(@PathVariable long id)
    {
        return subCategoryService.SearchSubCategoryById(id);
    }


    @GetMapping("/name")
    public SubCategory getSubCategoryByName(@PathVariable("name")String name)
    {
        return subCategoryService.getSubCategoryByName(name);
    }

//    @GetMapping("search")
//    public List<SubCategory> getByCategory(@PathVariable String category)
//    {
//        return subCategoryService.searchByName(category);
//    }



}
