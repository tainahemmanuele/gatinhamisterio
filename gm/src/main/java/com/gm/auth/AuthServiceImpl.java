package com.gm.auth;

import com.gm.jwt.JwtTokenUtil;
import com.gm.user.User;
import com.gm.user.UserRepository;
import com.gm.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Boolean register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            System.out.println("false");
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        final String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        System.out.println(user);
        //user.setRole(UserRole.CLIENT);

        userRepository.save(user);
        return true;
    }

    @Override
    public String login(String username, String password) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            return null;
        }
        System.out.println(userDetails.getPassword());
        System.out.println(password);

        if (!BCrypt.checkpw(password, userDetails.getPassword())) {
            return null;
        }
        final String token = jwtTokenUtil.generateToken(username);
        System.out.println(token);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        return jwtTokenUtil.refreshToken(oldToken);
    }
}