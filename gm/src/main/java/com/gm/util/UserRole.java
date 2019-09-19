package com.gm.util;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_USER");

    public static final GrantedAuthority ADM = new SimpleGrantedAuthority("ROLE_ADMIN");
    public static final GrantedAuthority USER = new SimpleGrantedAuthority("ROLE_USER");

    private String userRole;
    UserRole(String role) {
        this.userRole = role;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public GrantedAuthority toAuthotity(String role){
        switch(role){
            case "ROLE_ADMIN": return ADM;
            case "ROLE_USER": return USER;
        }
        return null;
    }
}