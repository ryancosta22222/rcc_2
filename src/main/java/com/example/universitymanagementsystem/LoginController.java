package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    // Hard-coded credentials for demonstration
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";
    private final String USER_USERNAME = "user";
    private final String USER_PASSWORD = "user123";

    @FXML
    public void handleLogin(ActionEvent event) {
        System.out.println("Login button clicked");
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fxmlToLoad = null;
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            fxmlToLoad = "/fxml/AdminDashboard.fxml";
        } else if (username.equals(USER_USERNAME) && password.equals(USER_PASSWORD)) {
            fxmlToLoad = "/fxml/UserDashboard.fxml";
        } else {
            System.out.println("Invalid credentials");
            return;
        }
        try {
            Parent dashboard = FXMLLoader.load(getClass().getResource(fxmlToLoad));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(dashboard));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



