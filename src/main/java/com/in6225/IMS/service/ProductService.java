package com.in6225.IMS.service;

import com.in6225.IMS.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    // Method to create a new product
    ProductDTO createProduct(ProductDTO productDTO);

    // Method to update an existing product
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    // Method to get a product by its ID
    ProductDTO getProductById(Long productId);

    // Method to get all products
    List<ProductDTO> getAllProducts();

    // Method to delete a product by its ID
    void deleteProduct(Long productId);
}
