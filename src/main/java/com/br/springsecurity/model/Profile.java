package com.br.springsecurity.model;


import java.util.Arrays;

public enum Profile {

    ADMIN("Admin"),
    MANAGER("Test"),
    USER("User");

    private String description;

    Profile(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Profile descriptionToEnum(String description) {
        return Arrays
                .stream(Profile.values())
                .filter(profiles -> profiles.name().equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("error to get profile"));
    }
}
