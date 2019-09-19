package com.gm.order;

import com.gm.user.User;
import com.gm.user.UserService;
import com.gm.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAll(Authentication auth) {
        if (auth == null)
            return new ResponseEntity<List<Order>>(HttpStatus.UNAUTHORIZED);

        List<Order> orders = new ArrayList<>();
        if (auth.getAuthorities().contains(UserRole.ADM)){
            orders = orderService.getAll();
        } else if (auth.getAuthorities().contains(UserRole.USER)){
            orders = orderService.findByUserEmail(auth.getName());
        }

        if (orders.isEmpty())
            return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
        return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        System.out.println("GET Order BY ID " + id + "...");
        Order order = orderService.getById(id);
        return (order != null)?
                new ResponseEntity<Order>(order, HttpStatus.OK):
                new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order, Authentication auth){
        System.out.println("CREATE ORDER " + order.toString());
        Order newOrder = null;
        if (auth.getAuthorities().contains(UserRole.ADM) || order.getUser().getId() == userService.getByEmail(auth.getName()).getId())
            newOrder = orderService.create(order);

        if (newOrder==null)
            return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Order>(newOrder,HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        System.out.println("DELETE order " + id + "...");

        if (orderService.delete(id)) {
            return new ResponseEntity<String>("Order has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete. Order does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
