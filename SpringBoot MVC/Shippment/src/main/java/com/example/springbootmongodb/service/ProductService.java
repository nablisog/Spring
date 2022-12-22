package com.example.springbootmongodb.service;

import com.example.springbootmongodb.entity.Product;
import com.example.springbootmongodb.entity.User;
import com.example.springbootmongodb.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void updateProduct(String id, Product product) {
        Product update = productRepository.findById(id).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(product,update);
        productRepository.save(update);
    }

    public void register(Product product) {
        productRepository.save(product);
    }

    public boolean exit(String name) {
       return  productRepository.findProductByName(name) != null;
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

}
