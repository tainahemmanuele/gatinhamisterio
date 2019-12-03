package com.gm.kafka;

import com.gm.rating.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@EnableKafka
@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String RATE = "kafka_rating";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String msg) {
        logger.info(String.format("#### -> Producing message -> %s", msg));
        this.kafkaTemplate.send(RATE, msg);
    }
}