package com.example.springbootmongodb.service;

import com.example.springbootmongodb.entity.Order;
import com.example.springbootmongodb.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("erishippment@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }
    public void sendOrder(User user, Order order){
        sendEmail(user.getEmail(),"Order Confirmation ",
                "Order Confirmation for:- " + "\n"
                        + " Sender:- " +    order.getSender().getFirstName() + " "
                        + order.getSender().getLastName() + "\n"
                        + " Order ID:-  " + order.getId() + "\n"
                        + " Receiver:- " + order.getReceiver().getFirstName()
                        + " " + order.getReceiver().getLastName() + "\n"
                        + " Email: " + order.getReceiver().getEmail() + "\n"
                        + " Telephone: " + order.getReceiver().getTlf() + "\n"
                        + " Total Price: " + order.getTotalPrice()+"kr" + "\n"
                        + " Order Date: " + order.getOrderDate());


    }
}
