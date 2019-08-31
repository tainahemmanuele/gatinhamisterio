package com.gm.user;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gm.order.Order;
import com.gm.order.OrderService;
import com.gm.subscription.Subscription;
import com.gm.subscription.SubscriptionService;
import com.gm.user.User;
import com.gm.order.OrderRepository;
import com.gm.subscription.SubscriptionRepository;
import com.gm.user.UserRepository;
import com.gm.util.UserRole;
import com.gm.util.Validator;
import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private Validator validator = new Validator();

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private OrderService orderService;

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
        return userData.isPresent() ? userData.get() : null;
    }

    public List<User> getByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public User create(User user)  throws ValidatorException{
        User userAux = validCreate(user);
        if (userAux != null) {
            return userRepository.save(user);
        }
        return userAux;
    }

    public User update(Long id, User user)  throws ValidatorException {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User savedUser = validUpdate(userData.get(), user);
            if (savedUser != null) {
                if (!savedUser.getEmail().equals(user.getEmail()) || savedUser.getId() != user.getId()) {
                    return null;
                } else {
                    return userRepository.save(savedUser);
                }
            } else {
                return null;
            }
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

    private boolean userEmailExists(String email) {
        Iterable<User> usersIterator = userRepository.findAll();
        for (User user : usersIterator) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private boolean userCPFExists(String CPF){
        Iterable<User> usersIterator = userRepository.findAll();
        for (User user : usersIterator) {
            if (user.getCPF().equals(CPF)) {
                return true;
            }
        }
        return false;

    }
    private User validCreate(User user)  throws ValidatorException {
        if (validator.validString(user.getName()) && validator.validEmail(user.getEmail())
                && validator.validPassword(user.getPassword()) && validator.validCPF(user.getCPF())) {
            if (!(userEmailExists(user.getEmail()) && userCPFExists(user.getCPF()))) {
                user.setName(user.getName());
                user.setPassword(user.getPassword());
                user.setRole(user.getRole());
                user.setEmail(user.getEmail());
                user.setCpf(user.getCPF());
                return user;
            } else {
                throw new ValidatorException("Email alread exists or CPF already is being used");
            }
        } else {
            return null;
        }

    }

    private User validUpdate(User user, User userUpdate)  throws ValidatorException{
        if (validator.validString(userUpdate.getName()) && validator.validEmail(userUpdate.getEmail())
                && validator.validPassword(userUpdate.getPassword())) {
            user.setName(userUpdate.getName());
            user.setPassword(userUpdate.getPassword());
            user.setRole(userUpdate.getRole());
            user.setEmail(userUpdate.getEmail());
            user.setCpf(userUpdate.getCPF());
            return user;

        } else {
            throw new ValidatorException("Email alread exists or CPF already is being used");
        }

    }
    public List<Subscription> findSubscriptionByUserId(Long id){
        Iterable<Order> orderData = orderService.findByUserId(id);
        List<Subscription> subs = new ArrayList<>();
        for (Order o: orderData) {
            subs.add(o.getSubscription());
        }
        return subs;
    }

    public Order createOrderByUserAndSubscription(long uid, long sid) throws ValidatorException{
        Order order = orderService.createByUserAndSubscription(uid,sid);

        if (order == null)
            throw new ValidatorException("User already has this subscription");

        User user = getById(uid);
        Subscription sub = subscriptionService.getById(sid);

        order.setUser(user);
        order.setSubscription(sub);
        orderService.create(order);

        return order;
    }
}