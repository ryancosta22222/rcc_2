package com.example.universitymanagementsystem;

public class User {
    protected String username;
    protected String password;
    protected String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role; // Return the role
    }

    public void setRole(String role) {
        this.role = role;
    }
}
