package com.gm.subscription;

import com.gm.user.User;
import com.gm.util.ValidatorException;
import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        System.out.println("GETTING ALL USERS...");
        return new ResponseEntity<List<Subscription>>(subscriptionService.getAll(),HttpStatus.OK) ;
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("id") Long id) {
        System.out.println("GET Subscription BY ID " + id + "...");
        Subscription subscription = subscriptionService.getById(id);
        return (subscription != null)?
                new ResponseEntity<Subscription>(subscription, HttpStatus.OK):
                new ResponseEntity<Subscription>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/subscription")
    public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody Subscription subscription)  throws ValidatorException {
        System.out.println("CREATE SUBSCRIPTION " + subscription.toString());
        Subscription newSub = subscriptionService.create(subscription);
        if (newSub == null)
            return new ResponseEntity<Subscription>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Subscription>(newSub,HttpStatus.OK);
    }

    @DeleteMapping("/subscription/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        System.out.println("DELETE SUBSCRIPTION " + id + "...");

        if (subscriptionService.delete(id)) {
            return new ResponseEntity<String>("Subscription has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete. Subscription does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @ApiOperation(value="Recebe usuários de uma subscription")
    @GetMapping("/subscription/{id}/user")
    public ResponseEntity<List<User>> userGetSubscriptions(@PathVariable("id") Long id){
        return new ResponseEntity<List<User>>(subscriptionService.findUserBySubscriptionId(id),HttpStatus.OK);
    }
}
