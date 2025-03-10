package com.example.universitymanagementsystem;

public class Session {
    private static Session instance = null;
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user){
        this.currentUser = user;
    }

    public User getUser(){
        return currentUser;
    }
}
