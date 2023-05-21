package com.vicente.microservicioproductos.controller;

import com.vicente.microservicioproductos.model.Product;
import com.vicente.microservicioproductos.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findByd(id);
        if (optionalProduct.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalProduct.get());
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Validated @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Validated @RequestBody Product product, BindingResult bindingResult, @PathVariable String id) {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        Optional<Product> optionalProduct = productService.findByd(id);
        if (optionalProduct.isEmpty()) return ResponseEntity.notFound().build();
        Product productDB = optionalProduct.get();
        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findByd(id);
        if (optionalProduct.isEmpty()) return ResponseEntity.notFound().build();
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }
}
