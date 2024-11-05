package com.example.auth_shop.service.product;

import com.example.auth_shop.dto.ProductDto;
import com.example.auth_shop.model.Product;
import com.example.auth_shop.request.AddProductRequest;
import com.example.auth_shop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest reqs);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Product updateProduct(ProductUpdateRequest reqs, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String category, String brandName);
    List<Product> getProductsByName(String productName);
    List<Product> getProductsByBrandAndName(String brandName, String productName);
    Long countProductsByBrandAndName(String brand, String productName);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToProductDto(Product product);
}
