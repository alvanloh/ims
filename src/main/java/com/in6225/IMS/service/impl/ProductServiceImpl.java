package com.in6225.IMS.service.impl;

import com.in6225.IMS.dto.ProductDTO;
import com.in6225.IMS.entity.Product;
import com.in6225.IMS.mapper.ProductMapper;
import com.in6225.IMS.repository.ProductRepository;
import com.in6225.IMS.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toProductEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(productDTO.getName());
        //existingProduct.setQuantity(productDTO.getQuantity()); Disable Adjustment Here
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setSku(productDTO.getSku());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setReorder(productDTO.getReorder());
        existingProduct = productRepository.save(existingProduct);
        return productMapper.toProductDTO(existingProduct);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductDTOList(products);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
