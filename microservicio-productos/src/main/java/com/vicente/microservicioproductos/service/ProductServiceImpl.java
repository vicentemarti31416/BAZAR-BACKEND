package com.vicente.microservicioproductos.service;

import com.vicente.microservicioproductos.model.Product;
import com.vicente.microservicioproductos.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findByd(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductsWithOptionalColorTrue(int numberOfElements) {
        return productRepository.findProductsWithOptionalColorTrue(numberOfElements);
    }

    @Override
    public List<Product> findProductsWithOptionalColorFalse(int numberOfElements) {
        return productRepository.findProductsWithOptionalColorFalse(numberOfElements);
    }

    @Override
    public List<Product> findByColorCode(Integer colorCode) {
        return productRepository.findByColorCode(colorCode);
    }

}
