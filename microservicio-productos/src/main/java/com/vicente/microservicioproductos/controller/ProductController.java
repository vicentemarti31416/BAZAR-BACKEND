package com.vicente.microservicioproductos.controller;

import com.vicente.microservicioproductos.model.Photo;
import com.vicente.microservicioproductos.model.Product;
import com.vicente.microservicioproductos.service.ImageUploadService;
import com.vicente.microservicioproductos.service.PhotoService;
import com.vicente.microservicioproductos.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PhotoService photoService;
    private final ImageUploadService imageUploadService;

    public ProductController(ProductService productService, PhotoService photoService, ImageUploadService imageUploadService) {
        this.productService = productService;
        this.photoService = photoService;
        this.imageUploadService = imageUploadService;
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
    public ResponseEntity<?> saveWithPhoto(@RequestBody Product product, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        List<Photo> photos = new ArrayList<>(product.getPhotos());
        product.getPhotos().clear();

        productService.save(product);
        photos.forEach((photo -> photo.setProductId(product.getId())));
        System.out.println("photos = " + photos);
        product.getPhotos().addAll(photos);
        productService.save(product);
        System.out.println("product = " + product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Validated @RequestBody Product product, BindingResult bindingResult, @PathVariable String id) throws IOException {
        if (bindingResult.hasErrors()) return validate(bindingResult);
        Optional<Product> optionalProduct = productService.findByd(id);
        if (optionalProduct.isEmpty()) return ResponseEntity.notFound().build();
        Product productDB = optionalProduct.get();

        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());
        productDB.setDescription(product.getDescription());
        productDB.setColor(product.getColor());
        productDB.setIva(product.getIva());

        List<Photo> existingPhotos = productDB.getPhotos();
        List<Photo> distinctPhotos = product
                .getPhotos()
                .stream()
                .filter((photo -> !existingPhotos.contains(photo)))
                .toList();
        photoService.saveAll(distinctPhotos);


        productDB.getPhotos().addAll(distinctPhotos);
        productDB.getPhotos().forEach((photo) -> photo.setProductId(product.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDB));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> save(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageUploadService.uploadImage(file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findByd(id);
        if (optionalProduct.isEmpty()) return ResponseEntity.notFound().build();
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{publicId}/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String publicId, @PathVariable String id) {
        Optional<Product> optionalProduct = productService.findByd(id);
        if (optionalProduct.isEmpty()) return ResponseEntity.notFound().build();
        Product productDB = optionalProduct.get();
        List<Photo> photos = productDB
                .getPhotos()
                .stream()
                .filter((photo) -> !photo.getPublicId().equals(publicId))
                .toList();
        productDB.getPhotos().clear();
        productDB.getPhotos().addAll(photos);
        productService.save(productDB);
        imageUploadService.deleteImage(publicId);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }
}
