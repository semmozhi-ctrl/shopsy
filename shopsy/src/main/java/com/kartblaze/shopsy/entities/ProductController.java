package com.kartblaze.shopsy.entities;


import com.kartblaze.shopsy.repositories.CartRepository;
import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class ProductController {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    public ProductController(ProductRepository productRepository, CartRepository cartRepository) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public String listProducts(Model model, @RequestParam(required = false) String category) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            return "product-detail";
        } else {
            return "error/404"; // Or any other error page
        }
    }
}