package com.java.kafkademo.kafka;

import com.java.kafkademo.payload.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics ="${spring.kafka.topic-json.name}",groupId ="${spring.kafka.group-id}")
    public void consume(User user){

        LOGGER.info(String.format("Json Message recieved -> %s", user.toString()));
    }
}
