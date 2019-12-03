package com.gm.rating;

import com.gm.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RatingController {
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String RATE = "kafka_rating";

    private final Producer producer;

    @Autowired
    RatingController(Producer producer) {
        this.producer = producer;
    }

    @Autowired
    private RatingService ratingService;

    @PostMapping("/rating/{message}")
    public String publishRating(@PathVariable("message") final String msg){
        this.producer.sendMessage(msg);
        return "Rating Published successfully";
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Rating>> getRatings() {
        return new ResponseEntity<List<Rating>>(ratingService.getAll(), HttpStatus.OK);
    }

}
