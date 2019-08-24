package com.gm.service;

import com.gm.model.Subscription;
import com.gm.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription getById(Long id) {
        Optional<Subscription> subscriptionData = subscriptionRepository.findById(id);
        if(subscriptionData.isPresent()) {
            return subscriptionData.get();
        } else {
            return null;
        }
    }

    public List<Subscription> getAll(){
        List<Subscription> subscriptionList = new ArrayList<>();
        Iterable<Subscription> subscriptions = subscriptionRepository.findAll();
        for (Subscription subscription : subscriptions) {
            subscriptionList.add(subscription);
        }
        return subscriptionList;
    }


    public boolean delete(Long id) {
        try {
            subscriptionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Subscription create(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }



}
