package com.example.product_management.controller;

import com.example.product_management.model.Category;
import com.example.product_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryID}")
    public ResponseEntity<Category> getCategory(@PathVariable int categoryID) {
        try {
            Category category = categoryService.getCategoryById(categoryID);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryID}")
    public ResponseEntity<String> deleteCategory(@PathVariable int categoryID) {
        try {
            return new ResponseEntity<>(categoryService.deleteCategory(categoryID), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("admin/categories/{categoryID}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable int categoryID) {
        try {
            Category savedCategory = categoryService.updateCategory(category, categoryID);
            return new ResponseEntity<>("Category with id " + categoryID + " updated", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
