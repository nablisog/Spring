package com.ecommerce.controller;

import com.ecommerce.dto.PurchaseResponse;
import com.ecommerce.service.CheckOutService;
import com.ecommerce.dto.PaymentInfo;
import com.ecommerce.dto.Purchase;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/checkout")
public class CheckOutController {
    @Autowired
    private CheckOutService checkOutService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        PurchaseResponse purchaseResponse = checkOutService.placeOrder(purchase);
        return purchaseResponse;
    }

    @PostMapping("/payment_intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkOutService.createPaymentIntent(paymentInfo);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
