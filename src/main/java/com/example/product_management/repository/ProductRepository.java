package com.example.product_management.repository;

import com.example.product_management.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    Page<Product> findByCategory(String category, Pageable pageable);

    void delete(Product product);

    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(int id);

    List<Product> findByTitleContainingIgnoreCase(String title);
}
