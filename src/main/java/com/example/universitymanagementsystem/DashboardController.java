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
 * Controller class for the User/Admin Dashboard.
 * This class handles navigation to different management sections.
 */
public class DashboardController {

    /**
     * Opens the Subject Management screen.
     * This method is triggered when the "Manage Subjects" button is clicked in the dashboard.
     * It loads the SubjectManagement.fxml file and updates the current stage.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void openSubjectManagement(ActionEvent event) throws IOException {
        // Loads the Subject Management UI from SubjectManagement.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SubjectManagement.fxml"));

        // Gets the current stage (window) from the button click event
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        // Sets the new scene (Subject Management) in the current stage
        stage.setScene(new Scene(root));
    }

    /**
     * Opens the Course Management screen.
     * This method is triggered when the "Manage Courses" button is clicked in the dashboard.
     * It loads the CourseManagement.fxml file and updates the current stage.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void openCourseManagement(ActionEvent event) throws IOException {
        // Loads the Course Management UI from CourseManagement.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CourseManagement.fxml"));

        // Gets the current stage (window) from the button click event
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        // Sets the new scene (Course Management) in the current stage
        stage.setScene(new Scene(root));
    }

    /**
     * Opens the Faculty Management screen.
     * This method is triggered when the "Manage Faculty" button is clicked in the dashboard.
     * It loads the FacultyManagement.fxml file and updates the current stage.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void openFacultyManagement(ActionEvent event) throws IOException {
        // Loads the Faculty Management UI from FacultyManagement.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FacultyManagement.fxml"));

        // Gets the current stage (window) from the button click event
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        // Sets the new scene (Faculty Management) in the current stage
        stage.setScene(new Scene(root));
    }
}
