package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserProfileController {

    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;

    @FXML
    private void initialize(){
        User user = Session.getInstance().getUser();
        if(user != null){
            usernameLabel.setText(user.getUsername());
            roleLabel.setText(user.getRole());
        }
    }
}
