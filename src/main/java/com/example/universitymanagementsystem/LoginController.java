package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // Hard-coded credentials for demonstration.
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";
    private final String STUDENT_USERNAME = "student";
    private final String STUDENT_PASSWORD = "student123";


    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String fxmlToLoad = null;
        User user = null;

        if(username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)){
            user = new Admin(username, password);
            user.setRole("ADMIN");  // Set the role for admin
            fxmlToLoad = "/fxml/AdminMainLayout.fxml";
        } else if(username.equals(STUDENT_USERNAME) && password.equals(STUDENT_PASSWORD)){
            // Create a sample student object with hardcoded details
            user = new Student(username, password, "S12345", "John Doe", "john@example.com", "Sophomore", "Fall 2025");
            user.setRole("STUDENT");  // Set the role for student
            fxmlToLoad = "/fxml/UserMainLayout.fxml";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check your username and password.");
            alert.showAndWait();
            return;
        }

        // Save logged-in user.
        Session.getInstance().setUser(user);

        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlToLoad));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}


