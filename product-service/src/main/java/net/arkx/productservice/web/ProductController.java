package net.arkx.productservice.web;

import net.arkx.productservice.entities.Product;
import net.arkx.productservice.entities.Promo;
import net.arkx.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void addProductToSubcateg(@RequestBody Product produit, @RequestParam("subCategoryIds") List<Long> subCategoryIds)
    {
        productService.addProductToSubcategories(produit,subCategoryIds);
    }

    @GetMapping
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long idToDelete)
    {

        productService.delete(idToDelete);

    }
    @PostMapping("promos")
    public void addPromoWithProducts(@RequestBody Promo promo){
        productService.addPromo(promo);
    }


}
