package com.example.product_management.controller;

import com.example.product_management.model.Category;
import com.example.product_management.model.Product;
import com.example.product_management.repository.ProductRepository;
import com.example.product_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/public/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/admin/products")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productID}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productID) {
        try {
            return new ResponseEntity<>(productService.deleteProduct(productID), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("admin/products/{productID}")
    public ResponseEntity<String> updateProduct(@RequestBody Product product,
                                                 @PathVariable int productID) {
        try {
            Product savedProduct = productService.changeProduct(product, productID);
            return new ResponseEntity<>("Product with id " + productID + " updated", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
