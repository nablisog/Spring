package com.java.kafkademo.kafka;

import com.java.kafkademo.payload.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class JsonKafkaProducer {
    @Value("${spring.kafka.topic-json.name}")
    private String jsonTopicName;
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);
    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User data){
        LOGGER.info(String.format("Message sent -> %s", data.toString()));
        Message<User> message = MessageBuilder.withPayload(data)
                                .setHeader(KafkaHeaders.TOPIC,jsonTopicName).build();
        kafkaTemplate.send(message);
    }

}
