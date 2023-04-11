package com.java.kafkademo.kafka;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="${spring.kafka.topic.name}", groupId = "${spring.kafka.group-id}")
    public void consume(String message){
        LOGGER.info(String.format("Message received -> %s",message));
    }
}
