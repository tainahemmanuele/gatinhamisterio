package com.gm.controller;

import com.gm.model.Order;
import com.gm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public List<Order> getAllOrder() {
        System.out.println("GETTING ALL USERS...");
        return orderService.getAll();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        System.out.println("GET Subscription BY ID " + id + "...");
        Order order = orderService.getById(id);
        return (order != null)?
                new ResponseEntity<Order>(order, HttpStatus.OK):
                new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/order")
    public Order setCartProducts(@Valid @RequestBody Order order){
        System.out.println("CREATE SUBSCRIPTION " + order.toString());
        return orderService.create(order);
    }
}
