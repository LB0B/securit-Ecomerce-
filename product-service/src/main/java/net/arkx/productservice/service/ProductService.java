package net.arkx.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import net.arkx.productservice.entities.Product;
import net.arkx.productservice.entities.Promo;
import net.arkx.productservice.entities.SubCategory;
import net.arkx.productservice.repository.ProductRepository;
import net.arkx.productservice.repository.PromoRepository;
import net.arkx.productservice.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final SubCategoryRepository subcategoryRepository;
    private final PromoRepository promoRepository;

    public ProductService(ProductRepository productRepository, SubCategoryRepository subCategoryRepository, PromoRepository promoRepository)
    {
        this.productRepository = productRepository;
        this.subcategoryRepository=subCategoryRepository;
        this.promoRepository = promoRepository;
    }
    //code



    public void addProductToSubcategories(Product product, List<Long> subcategoryIds) {
        Set<SubCategory> subcategories = new HashSet<>();
        for (Long subcategoryId : subcategoryIds) {
            SubCategory subcategory = subcategoryRepository.findById(subcategoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Subcategory not found"));
            subcategories.add(subcategory);
        }
        product.setSubcategories(subcategories);
        productRepository.save(product);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }
    public Product getProductById(Long idProduct)
    {
        return productRepository.findById(idProduct).get();
    }

    public void delete(Long idToDelete)
    {
        productRepository.deleteById(idToDelete);
    }

    //Add products to promo
    public void addPromo(Promo promo){
        promo.getProductsId().forEach(dto -> {
            Product product = getProductById(dto.getId());
            product.setPromo(true);
            product.setDiscount(dto.getDiscount());
            productRepository.save(product);
        });
        promoRepository.save(promo);
    }

}
