package com.vicente.microservicioproductos.service;

import com.vicente.microservicioproductos.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findByd(String id);
    Product save(Product product);
    void deleteById(String id);
}
