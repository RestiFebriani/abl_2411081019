package com.resti.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import com.resti.product.entity.Product;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = new Product();
        product.setId(id);
        product.setNama("Product" + id);
        product.setDescription("description: " + "Laptop Asus TUF Gaming FX506LH");
        product.setPrice("100000.0");
        return ResponseEntity.ok(product);
    }

}
