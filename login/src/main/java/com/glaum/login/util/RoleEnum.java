package com.glaum.login.util;

public enum RoleEnum {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
