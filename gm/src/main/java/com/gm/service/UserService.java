package com.gm.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gm.model.User;
import com.gm.repository.UserRepository;
import com.gm.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Iterable<User> userData = userRepository.findAll();
        userData.forEach(list::add);

        return list;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {
        Optional<User> userData = userRepository.findById(id);
        return userData.isPresent()? userData.get() : null;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
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

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> getUserByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
}