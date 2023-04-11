package com.java.stockservice.kafka;

import com.java.basedomain.dto.OrderEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent orderEvent){
        LOGGER.info(String.format("Order event recieved in stock service => %s",orderEvent.toString()));
        //save the order event into database
        //stock repository.save(orderEvent)

    }
}
