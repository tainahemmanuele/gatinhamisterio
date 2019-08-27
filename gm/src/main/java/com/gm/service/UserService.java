package com.gm.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.gm.model.Order;
import com.gm.model.Subscription;
import com.gm.model.User;
import com.gm.repository.SubscriptionRepository;
import com.gm.repository.OrderRepository;
import com.gm.repository.UserRepository;
import com.gm.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Iterable<User> userData = userRepository.findAll();
        userData.forEach(list::add);

        return list;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getById(Long id) {
        Optional<User> userData = userRepository.findById(id);
        return userData.isPresent()? userData.get() : null;
    }

    public List<User> getByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User savedUser = userData.get();
            savedUser.setName(user.getName());
            savedUser.setPassword(user.getPassword());
            savedUser.setRole(user.getRole());
            if (!savedUser.getEmail().equals(user.getEmail()) || savedUser.getId() != user.getId()) {
                return null;
            }
            return userRepository.save(savedUser);
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Subscription> findSubscriptionByUserId(Long id){
        Iterable<Order> orderData = orderRepository.findAll();
        List<Subscription> subs = new ArrayList<>();
        for (Order o: orderData) {
            if (o.getUser().getId() == id)
                subs.add(o.getSubscription());
        }
        return subs;
    }
}