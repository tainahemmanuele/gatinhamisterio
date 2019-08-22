package com.gm.controller;

import com.gm.model.Subscription;
import com.gm.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscription")
    public List<Subscription> getAllSubscriptions() {
        System.out.println("GETTING ALL USERS...");
        return subscriptionService.getAllSubscription();
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("id") Long id) {
        System.out.println("GET Subscription BY ID " + id + "...");
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return (subscription != null)?
                new ResponseEntity<Subscription>(subscription, HttpStatus.OK):
                new ResponseEntity<Subscription>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/subscription")
    public Subscription setCartProducts(@Valid @RequestBody Subscription subscription){
        System.out.println("CREATE SUBSCRIPTION " + subscription.toString());
        return subscriptionService.createSubscription(subscription);
    }

}
