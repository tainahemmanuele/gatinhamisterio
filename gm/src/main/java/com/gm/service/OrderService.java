package com.gm.service;

import com.gm.model.Order;
import com.gm.repository.OrderRepository;
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
        Order ordexAux = auxCreate(order);
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

    private Order auxCreate(Order order){
        Iterable<Order> orderIterator = orderRepository.findAll();
        for (Order auxOrder : orderIterator){
            if(auxOrder.getUser().equals(order.getUser()) && auxOrder.getSubscription().getType().equals(order.getSubscription().getType())){
                return null;
            }
        }
        return order;
    }
}
