package com.gm.order;

import com.gm.order.Order;
import com.gm.order.OrderRepository;
import com.gm.subscription.Subscription;
import com.gm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAll(){
        List<Order> orderList = new ArrayList<Order>();
        Iterable<Order> orderIterator = orderRepository.findAll();
        for (Order order : orderIterator){
            orderList.add(order);
        }
        return orderList;
    }

    public Order create(Order order){
        Order ordexAux = validCreate(order);
        if (ordexAux != null) {
            return orderRepository.save(order);
        } else{
            return null;
        }
    }

    public boolean delete(Long id){
        Optional<Order> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Order getById(Long id){
        Optional<Order> orderData = orderRepository.findById(id);
        if(orderData.isPresent()){
            Order order = orderData.get();
            return order;
        }else{
            return null; //posteriormente tratar isso com exceção
        }
    }

    public Order update(Long id, Order orderUpdate){
        Optional<Order> orderData = orderRepository.findById(id);
        if(orderData.isPresent()){
            Order order = orderData.get();
            order.setDispatchStatus(orderUpdate.getDispatchStatus());
            order.setPaymentStatus(orderUpdate.getPaymentStatus());
            order.setPaymentType(orderUpdate.getPaymentType());
            order.setQuantity(orderUpdate.getQuantity());
            order.setSubscription(orderUpdate.getSubscription());
            order.setUser(orderUpdate.getUser());
            return orderRepository.save(order);
        }else{
            return null; //posteriormente tratar isso com exceção
        }
    }

    private Order validCreate(Order order){
        Iterable<Order> orderIterator = orderRepository.findAll();
        for (Order auxOrder : orderIterator){
            if(auxOrder.getUser().equals(order.getUser()) && auxOrder.getSubscription().getType().equals(order.getSubscription().getType())){
                return null;
            }
        }
        return order;
    }

    public Order createByUserAndSubscription(long user, long subscription){
        return orderRepository.findOrderBySubscriptionAndSubscription(user,subscription);
    }

    public List<Order> findByUserId(long id){
        return orderRepository.findOrderByUserId(id);
    }
    public List<Order> findByUserEmail(String email){
        return orderRepository.findOrderByUserEmail(email);
    }

    public List<Order> findBySubscriptionId(long id){
        return orderRepository.findOrderBySubscriptionId(id);
    }
}
