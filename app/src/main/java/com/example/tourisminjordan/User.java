package com.example.tourisminjordan;

public class User {
    private String name;
    private String email;

    public User() {}

    public User(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
