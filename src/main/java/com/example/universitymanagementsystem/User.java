package com.example.universitymanagementsystem;

public abstract class User {
    protected String username;
    protected String password; // For demonstration only.
    protected String role; // "ADMIN" or "STUDENT"

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername(){
        return username;
    }

    public String getRole(){
        return role;
    }
}
