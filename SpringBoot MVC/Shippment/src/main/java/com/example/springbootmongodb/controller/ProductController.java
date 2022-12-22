package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.entity.Product;
import com.example.springbootmongodb.repository.ProductRepository;
import com.example.springbootmongodb.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllUsers(){
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Product product){
        if (productService.exit(product.getName())){
            return new ResponseEntity<>("Product Already Exist",HttpStatus.BAD_REQUEST);
        }
        productService.register(product);
        return ResponseEntity.ok("Product is Successfully registered");
    }
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id")String id, @Valid @RequestBody Product product) {
        if (productRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("Product doesn't exit",HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(id,product);
        return new ResponseEntity<>("Product has been updated", HttpStatus.OK);
    }
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        if (productRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("Product doesn't exit",HttpStatus.BAD_REQUEST);
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product has been deleted");
    }
}
