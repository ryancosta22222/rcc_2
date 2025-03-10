package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserDashboardController {
    @FXML private Label welcomeLabel;

    @FXML
    private void initialize(){
        User user = Session.getInstance().getUser();
        if(user != null){
            welcomeLabel.setText("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        }
    }
}
