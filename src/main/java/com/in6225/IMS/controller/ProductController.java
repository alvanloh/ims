// src/main/java/com/in6225/IMS/controller/ProductController.java
package com.in6225.IMS.controller;

import com.in6225.IMS.dto.ProductDTO;
import com.in6225.IMS.service.ProductService;
import jakarta.validation.Valid; // Import jakarta validation
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint to create a new product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) { // Add @Valid
        log.info("Request received to create product with SKU: {}", productDTO.getSku());
        ProductDTO createdProduct = productService.createProduct(productDTO);
        log.info("Product created successfully with ID: {}", createdProduct.getId());
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Endpoint to update an existing product
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductDTO productDTO) { // Add @Valid
        log.info("Request received to update product with ID: {}", productId);
        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        log.info("Product updated successfully with ID: {}", updatedProduct.getId());
        return ResponseEntity.ok(updatedProduct);
    }

    // Endpoint to get a product by its ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        log.info("Request received to get product by ID: {}", productId);
        ProductDTO productDTO = productService.getProductById(productId);
        log.info("Product found with ID: {}", productId);
        return ResponseEntity.ok(productDTO);
    }

    // Endpoint to get all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Request received to get all products");
        List<ProductDTO> products = productService.getAllProducts();
        log.info("Found {} products", products.size());
        return ResponseEntity.ok(products);
    }

    // Endpoint to delete a product by its ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        log.info("Request received to delete product with ID: {}", productId);
        productService.deleteProduct(productId);
        log.info("Product deleted successfully with ID: {}", productId);
        return ResponseEntity.noContent().build();
    }
}