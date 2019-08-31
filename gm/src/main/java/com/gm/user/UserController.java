package com.gm.user;

import com.gm.order.Order;
import com.gm.subscription.Subscription;
import com.gm.order.OrderService;
import com.gm.subscription.SubscriptionService;
import com.gm.util.UserRole;
import com.gm.util.ValidatorException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
//import sun.plugin.javascript.navig.Array;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@Api(value = "API REST de Usuário")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private OrderService orderService;
    EntityManager entityManager;
    @ApiOperation(value="Retorna uma lista de Usuários")
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("GETTING ALL USERS...");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty())
            return new ResponseEntity<List<User>>(users,HttpStatus.OK);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @ApiOperation(value="Retorna o usuário cujo id é {id}")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        System.out.println("GET USER BY ID "+ id + "...");
        User user = userService.getById(id);
        return (user != null)?
                new ResponseEntity<User>(user, HttpStatus.OK):
                new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        System.out.println("GET USER BY EMAIL "+ email + "...");
        User user = userService.getByEmail(email);
        return (user != null)?
                new ResponseEntity<User>(user, HttpStatus.OK):
                new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value="Cria um novo usuário")
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)  throws ValidatorException {
        System.out.println("CREATE USER " + user.getName() + "...");
        User userCreated = userService.create(user);
        if (userCreated != null){
            return new ResponseEntity<User>(userCreated, HttpStatus.OK);
        } else{
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value="Atualiza um usuário")
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user)  throws ValidatorException{
        User updatedUser = userService.update(id, user);
        if(updatedUser != null) {
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value="Deleta um usuário")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        System.out.println("DELETE USER " + id + "...");

        if (userService.delete(id)) {
            return new ResponseEntity<String>("User has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete. User does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @ApiOperation(value="Pega os usuários que são admins")
    @GetMapping("/user/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        System.out.println("GET ALL ADMINS...");
        List<User> userList = userService.getByRole(UserRole.ADMIN);
        if (userList != null) {
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value="Pega os usuários que são clientes")
    @GetMapping("/user/clients")
    public ResponseEntity<List<User>> getAllClients() {
        System.out.println("GET ALL CLIENTS...");
        List<User> userList = userService.getByRole(UserRole.CLIENT);
        if (userList != null) {
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @ApiOperation(value="Recebe subscriptions de um usuário")
    @GetMapping("/user/{id}/subscription")
    public ResponseEntity<List<Subscription>> userGetSubscriptions(@PathVariable("id") Long id){
        return new ResponseEntity<List<Subscription>>(userService.findSubscriptionByUserId(id),HttpStatus.OK);
    }

    @ApiOperation(value="Cria uma nova order")
    @PostMapping("/user/{uid}/subscription/{sid}")
    @Transactional
    public ResponseEntity<Order> userPostOrder(@PathVariable("uid") Long uid,@PathVariable("sid") Long sid, @Valid @RequestBody Order order) throws ValidatorException{

        return new ResponseEntity<Order>(order,HttpStatus.OK);
    }

}