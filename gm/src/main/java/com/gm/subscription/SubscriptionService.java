package com.gm.subscription;

import com.gm.order.Order;
import com.gm.order.OrderService;
import com.gm.subscription.Subscription;
import com.gm.user.User;
import com.gm.order.OrderRepository;
import com.gm.subscription.SubscriptionRepository;
import com.gm.user.UserRepository;
import com.gm.util.Validator;
import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private OrderService orderService;

    private Validator validator = new Validator();

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
    public Subscription create(Subscription subscription) throws ValidatorException {
        Subscription subscriptionAux = validCreate(subscription);
        if (subscriptionAux != null) {
            return subscriptionRepository.save(subscription);
        } else{
            return null;
        }
    }


    public List<User> findUsersBySubscriptionId(Long id){
        Iterable<Order> orderData = orderService.findBySubscriptionId(id);
        List<User> users = new ArrayList<>();
        for (Order o: orderData) {
            users.add(o.getUser());
        }
        return users;
    }


    public Subscription validCreate(Subscription subscription) throws ValidatorException {
        if(validator.validSubscriptionType(subscription.getType()) && validator.validListProductAux(subscription.getBox().getProducts())
        && validator.validYearMonthSubscription(subscription.getSubscriptionYearMonth()) && validator.validValue(subscription.getPrice())){
            subscription.setBox(subscription.getBox());
            subscription.setPrice(subscription.getPrice());
            subscription.setType(subscription.getType());
            subscription.setSubscriptionYearMonth(subscription.getSubscriptionYearMonth());
            return subscription;

        }else{
            return null;
        }
    }
}
