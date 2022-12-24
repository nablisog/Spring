package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.dto.OrderDto;
import com.example.springbootmongodb.entity.Order;
import com.example.springbootmongodb.repository.OrderRepository;
import com.example.springbootmongodb.repository.UserRepository;
import com.example.springbootmongodb.service.OrderService;
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
public class OrderController{
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id")String id){
        if (orderRepository.findById(id).isEmpty()){
            return new ResponseEntity("Order doesn't exit", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderRepository.findById(id).get(), HttpStatus.OK);
    }
    @PostMapping("/makeOrder")
    public ResponseEntity<String> makeOrder(@Valid @RequestBody OrderDto dto){
        orderService.order(dto);
        return new ResponseEntity<>("Order has been confirmed",HttpStatus.CREATED);
    }
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable("id")String id, @Valid @RequestBody OrderDto details) {
        if (orderRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("Order doesn't exit",HttpStatus.BAD_REQUEST);
        }
        orderService.updateOrder(id,details);
        return new ResponseEntity<>("Order has been updated", HttpStatus.OK);
    }
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        if (orderRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("Order doesn't exit",HttpStatus.BAD_REQUEST);
        }
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order has been deleted");
    }









}
