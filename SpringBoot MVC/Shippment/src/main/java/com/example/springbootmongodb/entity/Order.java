package com.example.springbootmongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {
    @Id
    private String id;
    @DBRef
    private User sender;
    @DBRef
    private User receiver;
    @DBRef
    @JsonIgnore
    private Product product;
    private List<ShoppingCart> productList;
    private int totalQuantity;
    private double totalPrice;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate orderDate;


}
