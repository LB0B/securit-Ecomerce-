package net.arkx.productservice.service;

import net.arkx.productservice.repository.ProductRepository;
import net.arkx.productservice.repository.PromoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
