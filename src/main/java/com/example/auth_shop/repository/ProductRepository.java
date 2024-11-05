package com.example.auth_shop.repository;

import com.example.auth_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String category, String brandName);

    List<Product> findByName(String productName);

    List<Product> findByBrandAndName(String brandName, String productName);

    Long countByBrandAndName(String brand, String productName);

    Product getProductById(Long productId);

    boolean existsByNameAndBrand(String name, String brand);
}
