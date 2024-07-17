package com.example.product_management.implement;

import com.example.product_management.model.Category;
import com.example.product_management.model.Product;
import com.example.product_management.repository.ProductRepository;
import com.example.product_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public void createProduct(Product product) {
        if (product.getImage() == null || product.getImage().isEmpty()) {
            product.setImage("default.jpg");
        }

        productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(int productID) {
        List<Product> products = productRepository.findAll();
        Product product = products.stream()
                .filter(c -> c.getId() == productID)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(product);
        return "Product with ID " + productID +" deleted successfully";
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {

        Product dbProduct = getProductById(product.getId());

        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setImage(imageName);

        Product updateProduct = productRepository.save(dbProduct);

        if (!ObjectUtils.isEmpty(updateProduct)) {
            if (!image.isEmpty()) {
                try {
                    File saveFile = new ClassPathResource("static/images").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +  File.separator
                            + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return updateProduct;
        }
        return null;
    }

    @Override
    public Product changeProduct(Product product, int productId) {
        List<Product> products = productRepository.findAll();

        Optional<Product> optionalProduct = products.stream()
                .filter(p -> p.getId() == productId)
                .findFirst();

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            if (product.getTitle() != null && !product.getTitle().isEmpty()) {
                existingProduct.setTitle(product.getTitle());
            }
            if (product.getDescription() != null && !product.getDescription().isEmpty()) {
                existingProduct.setDescription(product.getDescription());
            }
            if (product.getCategory() != null) {
                existingProduct.setCategory(product.getCategory());
            }
            if (product.getPrice() != null) {
                existingProduct.setPrice(product.getPrice());
            }
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                existingProduct.setImage(product.getImage());
            }
            return productRepository.save(existingProduct);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByTitleContainingIgnoreCase(name);
    }

}
