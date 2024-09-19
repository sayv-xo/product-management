package com.example.product_management.controller;

import com.example.product_management.model.Category;
import com.example.product_management.model.Product;
import com.example.product_management.service.CategoryService;
import com.example.product_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {

        Page<Product> products;
        Pageable pageable = PageRequest.of(page, size);
        if (category.isEmpty()) {
            products = productService.getAllProducts(pageable);
        } else {
            products = productService.findByCategory(category, pageable);
        }

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("category", category);
        model.addAttribute("size", size);
        return "index";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchProductsByName(query);
        model.addAttribute("products", products);
        return "storepage";
    }

    @GetMapping("/storepage")
    public String store(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
        List<Category> categories = categoryService.getAllCategories();
        List<Product> products = productService.getAllProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "storepage";
    }

    @GetMapping("/product")
    public String store(
            Model model,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {

        Page<Product> products;
        Pageable pageable = PageRequest.of(page, size);
        if (category.isEmpty()) {
            products = productService.getAllProducts(pageable);
        } else {
            products = productService.findByCategory(category, pageable);
        }

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("category", category);
        model.addAttribute("size", size);
        return "index";
    }
}