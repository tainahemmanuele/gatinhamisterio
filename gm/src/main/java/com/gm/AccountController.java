package com.gm;

import com.gm.auth.AuthService;
import com.gm.auth.AuthServiceImpl;
import com.gm.user.User;
import com.gm.user.UserService;
import com.gm.util.Validator;
import com.gm.util.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by nju.edu.cn.qaserver on 2017/6/27.
 */
@RestController
public class AccountController {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    UserService userService;


    private Validator validator = new Validator();
    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) throws ValidatorException {
        System.out.println("CREATE USER " + user.getName() + "...");

        if (authService.register(user)){
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.IM_USED);
        }
    }

    @GetMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestHeader String email, @RequestHeader String password) throws ValidatorException {
        if (!validator.validEmail(email)) {
            return new ResponseEntity<>(null, null, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String token = authService.login(email, password);
        System.out.print(token);

        if (token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@RequestHeader String token) throws AuthenticationException {
        String refreshedToken = authService.refresh(token);
        if (refreshedToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", refreshedToken);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }
}