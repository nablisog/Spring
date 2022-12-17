package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.entity.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart,String> {
}
