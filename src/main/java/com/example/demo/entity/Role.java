package com.example.demo.entity;

import java.util.Arrays;

public enum Role {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String type;

    Role(String type) {
        this.type = type;
    }

    public static Role parse(String type) {
        return Arrays.stream(values())
                .filter(role -> role.type.equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
