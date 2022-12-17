package com.example.springbootmongodb.service;
import com.example.springbootmongodb.dto.OrderDto;
import com.example.springbootmongodb.entity.Order;
import com.example.springbootmongodb.entity.Product;
import com.example.springbootmongodb.entity.ShoppingCart;
import com.example.springbootmongodb.entity.User;
import com.example.springbootmongodb.repository.OrderRepository;
import com.example.springbootmongodb.repository.ProductRepository;
import com.example.springbootmongodb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final EmailService mailSender;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public void order(OrderDto dto){
        Order order = new Order();
        User userSender = userRepository.findUserByEmail(dto.getEmailSender());
        User userReceiver= userRepository.findUserByEmail(dto.getEmailReceiver());
        for (var cart : dto.getCartItems()){
            Product product = productRepository.findProductByName(cart.getProductName());
            order.setSender(userSender);
            order.setReceiver(userReceiver);
            cart.setPrice(calculatePrice(cart.getQuantity(),product.getPrice()));
            order.setOrderDate(LocalDate.now());
            order.setProductList(dto.getCartItems());
        }
        Integer totalQuantity = order.getProductList().stream().map(ShoppingCart::getQuantity)
                .reduce(0,Integer::sum);
        Double totalPrice = order.getProductList().stream()
                .map(ShoppingCart::getPrice).reduce(0., Double::sum);
        DecimalFormat df = new DecimalFormat("0.00");
        order.setTotalPrice(Double.parseDouble(df.format(totalPrice)));
        order.setTotalQuantity(totalQuantity);
        orderRepository.save(order);
        mailSender.sendOrder(userSender,order);


    }

    private double calculatePrice(int quantity, double price){
        return price * quantity;

    }

    public void updateOrder(String id, OrderDto order) {
        Order updateOrder = orderRepository.findById(id).get();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(order,updateOrder);
        updateOrder.setOrderDate(LocalDate.now());
        orderRepository.save(updateOrder);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
