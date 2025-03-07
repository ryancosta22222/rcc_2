package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for the User Dashboard.
 * This class handles navigation for user-specific functionalities such as
 * viewing courses and events.
 */
public class UserDashboardController {

    /**
     * Opens the Course Management view when the "View Courses" button is clicked.
     *
     * This method loads the CourseManagement.fxml file, which contains the UI
     * for students to view available courses.
     *
     * @param event The event triggered by clicking the "View Courses" button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void openCourseManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/CourseManagement.fxml");
    }

    /**
     * Opens the Event Management view when the "View Events" button is clicked.
     *
     * This method loads the UserEvents.fxml file, which contains the UI
     * for students to view upcoming events.
     *
     * @param event The event triggered by clicking the "View Events" button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void openEventManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/UserEvents.fxml"); // New Event Management Page
    }

    /**
     * Utility method to navigate to different screens.
     *
     * This method loads the specified FXML file and updates the current stage with the new scene.
     *
     * @param event The event triggered by clicking a button.
     * @param fxmlFile The path of the FXML file to load.
     * @throws IOException If the FXML file cannot be loaded.
     */
    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        // Load the FXML file and create a new scene
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));

        // Get the current stage from the event source (the button clicked)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the stage
        stage.setScene(new Scene(root));

        // Show the updated stage
        stage.show();
    }
}
