package com.gm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gm.model.User;
import com.gm.repository.UserRepository;
import com.gm.util.UserRole;
import com.gm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private Validator validator = new Validator();

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

    public User create(User user) {
        User userAux = auxCreate(user);
        if (userAux != null) {
            return userRepository.save(user);
        }
        return userAux;
    }

    public User update(Long id, User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User savedUser = auxUpdate(userData.get(), user);
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

    private boolean searchUserEmail(String email) {
        Iterable<User> usersIterator = userRepository.findAll();
        for (User user : usersIterator) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;

    }

    private User auxCreate(User user) {
        if (validator.validString(user.getName()) && validator.validEmail(user.getEmail())
                && validator.validPassword(user.getPassword())) {
            if (!searchUserEmail(user.getEmail())) {
                user.setName(user.getName());
                user.setPassword(user.getPassword());
                user.setRole(user.getRole());
                user.setEmail(user.getEmail());
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private User auxUpdate(User user, User userUpdate) {
        if (validator.validString(userUpdate.getName()) && validator.validEmail(userUpdate.getEmail())
                && validator.validPassword(userUpdate.getPassword())) {
            user.setName(userUpdate.getName());
            user.setPassword(userUpdate.getPassword());
            user.setRole(userUpdate.getRole());
            user.setEmail(user.getEmail());
            return user;

        } else {
            return null;
        }

    }
}