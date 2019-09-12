package com.gm.auth;
import java.util.HashSet;
import java.util.Set;

import com.gm.user.User;
import com.gm.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getUserRole()));
        AccountCredentials acc = new AccountCredentials(user.getEmail(),user.getPassword(),grantedAuthorities);
        System.out.println("User: " + user.getName() + " Role: " + grantedAuthorities.toString());
        return new org.springframework.security.core.userdetails.User(acc.getEmail(),acc.getPassword(),acc.getAuthorities());
    }

}