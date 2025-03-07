package com.example.universitymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main entry point of the University Management System.
 * This class initializes the JavaFX application and loads the Login screen.
 */
public class Main extends Application {

    /**
     * The start method is called when the JavaFX application launches.
     * It sets up the primary stage (window) and loads the initial login screen.
     *
     * @param primaryStage The main window of the application.
     * @throws Exception If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the Login.fxml file from the "resources/fxml" directory
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

        // Set the title of the application window
        primaryStage.setTitle("University Management System - Login");

        // Set the scene (Login page) with the loaded FXML layout
        primaryStage.setScene(new Scene(root));

        // Display the window
        primaryStage.show();
    }

    /**
     * The main method serves as the application entry point.
     * It launches the JavaFX application, which in turn calls the start() method.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);  // Start the JavaFX application
    }
}
