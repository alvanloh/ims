package com.in6225.IMS.mapper;

import com.in6225.IMS.dto.ProductDTO;
import com.in6225.IMS.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    // Convert Product entity to ProductDTO
    public ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setSku(product.getSku());
        productDTO.setCategory(product.getCategory());
        productDTO.setReorder(product.getReorder());
        return productDTO;
    }

    // Convert ProductDTO to Product entity
    public Product toProductEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setSku(productDTO.getSku());
        product.setCategory(productDTO.getCategory());
        product.setReorder(productDTO.getReorder());
        return product;
    }

    // Convert a list of Product entities to a list of ProductDTOs
    public List<ProductDTO> toProductDTOList(List<Product> products) {
        return products.stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
}
