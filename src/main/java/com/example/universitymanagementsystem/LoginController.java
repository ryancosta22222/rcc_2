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

/**
 * Controller class for handling user login.
 * This class verifies user credentials and navigates to the corresponding dashboard (Admin or User).
 */
public class LoginController {

    // FXML components linked to the Login.fxml file
    @FXML private TextField usernameField;   // Text field where the user enters their username
    @FXML private PasswordField passwordField;  // Password field where the user enters their password
    @FXML private Button loginButton;   // Login button to trigger authentication

    // Hard-coded credentials for demonstration purposes
    private final String ADMIN_USERNAME = "admin";   // Admin username
    private final String ADMIN_PASSWORD = "admin123"; // Admin password
    private final String USER_USERNAME = "user";     // User (student) username
    private final String USER_PASSWORD = "user123";  // User (student) password

    /**
     * Handles the login button action.
     * Validates user credentials and loads the appropriate dashboard (Admin or User).
     *
     * @param event The event triggered by clicking the login button.
     */
    @FXML
    public void handleLogin(ActionEvent event) {
        System.out.println("Login button clicked");

        // Retrieve the entered username and password from the input fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fxmlToLoad = null;  // Variable to store which FXML file to load

        // Check if the entered credentials match the admin credentials
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            fxmlToLoad = "/fxml/AdminDashboard.fxml"; // Load the Admin Dashboard
        }
        // Check if the entered credentials match the user credentials
        else if (username.equals(USER_USERNAME) && password.equals(USER_PASSWORD)) {
            fxmlToLoad = "/fxml/UserDashboard.fxml"; // Load the User Dashboard
        }
        // If credentials do not match, print an error message and return
        else {
            System.out.println("Invalid credentials");
            return; // Exit the method without changing the scene
        }

        try {
            // Load the corresponding FXML file based on the determined role
            Parent dashboard = FXMLLoader.load(getClass().getResource(fxmlToLoad));

            // Get the current stage (window) using the event source (button click)
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

            // Set the new scene (dashboard) in the current stage
            stage.setScene(new Scene(dashboard));

            // Display the updated stage with the selected dashboard
            stage.show();
        } catch (IOException e) {
            // Print error details if the FXML file fails to load
            e.printStackTrace();
        }
    }
}
