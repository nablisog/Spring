package com.ecommerce.service;

import com.ecommerce.dao.CustomerRepository;
import com.ecommerce.dto.PurchaseResponse;
import com.ecommerce.dto.PaymentInfo;
import com.ecommerce.dto.Purchase;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CheckOutServiceImpl implements CheckOutService{

    private CustomerRepository customerRepository;

    public CheckOutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {

        this.customerRepository = customerRepository;

        // initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }



    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        String trackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(trackingNumber);
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();
        String email = customer.getEmail();
        Customer customerFromDb = customerRepository.findByEmail(email);
        if(customerFromDb != null){
            customer = customerFromDb;
        }
        customer.add(order);
        customerRepository.save(customer);
        return new PurchaseResponse(trackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }


    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("cards");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currencey", paymentInfo.getCurrency());
        params.put("receipt_email",paymentInfo.getEmailRcipient());
        params.put("payment_method_types", paymentMethodTypes);



        return PaymentIntent.create(params);
    }


}
