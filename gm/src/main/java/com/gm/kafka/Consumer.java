package com.gm.kafka;

import com.gm.rating.Rating;
import com.gm.rating.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@EnableKafka
@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String RATE = "kafka_rating";

    @Autowired
    private RatingService ratingService;

    @KafkaListener(topics = RATE, groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        Rating rating = new Rating();
        rating.setTextRating(message);
        ratingService.create(rating);
    }
}