package com.example.product_management.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.IOException;
import com.example.product_management.model.Category;
import com.example.product_management.model.Product;
import com.example.product_management.repository.CategoryRepository;
import com.example.product_management.service.CategoryService;
import com.example.product_management.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/category")
    public String adminCategory() {
        return "admin/category";
    }

    @GetMapping("/products")
    public String loadViewProduct(Model m) {
        m.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/load-add-products")
    public String adminAddProducts(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/add-products";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
                              HttpSession session) {

        try {
            String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
            product.setImage(imageName);
            Product saveProduct = productService.addProduct(product);

            if (!ObjectUtils.isEmpty(saveProduct)) {
                File saveFile = new ClassPathResource("static/images").getFile();
                File targetDirectory = new File(saveFile.getAbsolutePath() + File.separator );

                if (!targetDirectory.exists()) {
                    targetDirectory.mkdirs();
                }

                Path path = Paths.get(targetDirectory.getAbsolutePath() + File.separator + image.getOriginalFilename());
                System.out.println("Saving file to: " + path);

                if (!image.isEmpty()) {
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                }

                session.setAttribute("succMsg", "Product Saved Successfully");
            } else {
                session.setAttribute("errorMsg", "Something went wrong on the server");
            }
        } catch (IOException e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Error saving the product image: " + e.getMessage());
        }

        return "redirect:/admin/load-add-products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {
        try {
            new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model m) {
        m.addAttribute("product", productService.getProductById(id));
        m.addAttribute("categories", categoryService.getAllCategories());
        return "admin/edit_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image, HttpSession session) {
        System.out.println("update method running");
        if (product.getPrice() < 0 ) {
            session.setAttribute("errorMsg", "invalid Price");
        } else {
            Product updateProduct = productService.updateProduct(product, image);
            if (!ObjectUtils.isEmpty(updateProduct)) {
                session.setAttribute("succMsg", "Product update success");
            } else {
                session.setAttribute("errorMsg", "Something wrong on server");
            }
        }
        return "redirect:/admin/editProduct/" + product.getId();
    }


}
