package com.gm.auth;

import com.gm.user.User;

public interface AuthService {

    Boolean register(User user);

    String login(String username, String password);

    String refresh(String oldToken);
}