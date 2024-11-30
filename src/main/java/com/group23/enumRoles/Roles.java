package com.group23.enumRoles;

public enum Roles {
    ADMIN,
    USER;

    public String getRole() {
        return "ROLE_" + this.name(); // Prefix with "ROLE_" for Spring Security
    }
}