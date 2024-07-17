package com.example.product_management.service;

import com.example.product_management.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Page<Product> getAllProducts(Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);
    void createProduct(Product product);
    Product getProductById(int id);
    Product addProduct(Product product);
    String deleteProduct(int productID);
    Product updateProduct(Product product, MultipartFile file);
    Product changeProduct(Product product, int productID);
    List<Product> searchProductsByName(String query);
}
