package com.example.auth_shop.request;

import com.example.auth_shop.model.Category;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description ;
    private Category category;}
