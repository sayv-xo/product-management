package com.example.product_management.implement;

import com.example.product_management.model.Category;
import com.example.product_management.repository.CategoryRepository;
import com.example.product_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(int categoryID) {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.stream()
                .filter(c -> c.getCategoryID() == categoryID)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
        return "Category with ID " + categoryID +" deleted successfully";
    }

    @Override
    public Category getCategoryById(int categoryID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category.isPresent()) return category.get();
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }

    @Override
    public Category updateCategory(Category category, int categoryID) {
        List<Category> categories = categoryRepository.findAll();

        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryID() == categoryID)
                .findFirst();
        if (optionalCategory.isPresent()) {
            Category existCategory = optionalCategory.get();
            existCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existCategory);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}
