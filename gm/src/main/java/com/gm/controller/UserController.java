package com.gm.controller;

import com.gm.model.User;
import com.gm.service.UserService;
import com.gm.util.UserRole;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        System.out.println("GETTING ALL USERS...");
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        System.out.println("GET USER BY ID "+ id + "...");
        User user = userService.getUserById(id);
        return (user != null)?
                new ResponseEntity<User>(user, HttpStatus.OK):
                new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        System.out.println("GET USER BY EMAIL "+ email + "...");
        User user = userService.getUserByEmail(email);
        return (user != null)?
                new ResponseEntity<User>(user, HttpStatus.OK):
                new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        System.out.println("CREATE USER " + user.getName() + "...");
        return userService.createUser(user);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateProduct(@PathVariable("id") Long id, @RequestBody User user) {

        User updatedUser = userService.updateUser(id, user);
        if(updatedUser != null) {
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        System.out.println("DELETE USER " + id + "...");

        if (userService.deleteUser(id)) {
            return new ResponseEntity<String>("User has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/user/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        System.out.println("GET ALL ADMINS...");
        List<User> userList = userService.getUserByRole(UserRole.ADMIN);
        if (userList != null) {
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/clients")
    public ResponseEntity<List<User>> getAllClients() {
        System.out.println("GET ALL CLIENTS...");
        List<User> userList = userService.getUserByRole(UserRole.CLIENT);
        if (userList != null) {
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}