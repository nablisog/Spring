package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Product findProductByName(String name);



}
