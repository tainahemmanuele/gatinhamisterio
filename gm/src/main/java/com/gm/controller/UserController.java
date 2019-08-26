package com.gm.controller;

import com.gm.model.User;
import com.gm.service.UserService;
import com.gm.util.UserRole;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import sun.plugin.javascript.navig.Array;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("GETTING ALL USERS...");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty())
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

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

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        System.out.println("CREATE USER " + user.getName() + "...");
        User userCreated = userService.create(user);
        if (userCreated != null){
            return new ResponseEntity<User>(userCreated, HttpStatus.OK);
        } else{
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        if(updatedUser != null) {
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        System.out.println("DELETE USER " + id + "...");

        if (userService.delete(id)) {
            return new ResponseEntity<String>("User has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to delete.", HttpStatus.EXPECTATION_FAILED);
        }
    }

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
}