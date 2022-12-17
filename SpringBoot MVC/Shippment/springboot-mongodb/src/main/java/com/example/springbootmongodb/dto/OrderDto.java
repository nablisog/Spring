package com.example.springbootmongodb.dto;


import com.example.springbootmongodb.entity.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private List<ShoppingCart> cartItems;
    private String emailSender;
    private String emailReceiver;



}
