package com.example.product_management.service;

import com.example.product_management.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(int categoryID);
    Category updateCategory(Category category, int categoryID);
}
