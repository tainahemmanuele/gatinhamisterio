package com.gm.util;


public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_USER");

    private String userRole;

    UserRole(String role) {
        this.userRole = role;
    }

    public String getUserRole() {
        return this.userRole;
    }
}