package com.example.springbootmongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {
    private String productName;
    private int quantity;
    private double price;

}
