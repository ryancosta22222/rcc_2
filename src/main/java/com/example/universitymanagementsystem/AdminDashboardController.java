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
 * Controller class for the Admin Dashboard.
 * This class handles navigation to different management sections for the Admin role.
 */
public class AdminDashboardController {

    /**
     * Handles navigation to the Course Management screen.
     * This method is triggered when the "Manage Courses" button is clicked in AdminDashboard.fxml.
     * It loads the AdminCourses.fxml file.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void openCourseManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/AdminCourses.fxml");
    }

    /**
     * Handles navigation to the Subject Management screen.
     * This method is triggered when the "Manage Subjects" button is clicked in AdminDashboard.fxml.
     * It loads the SubjectManagement.fxml file.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void openSubjectManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/SubjectManagement.fxml");
    }

    /**
     * Handles navigation to the Student Management screen.
     * This method is triggered when the "Manage Students" button is clicked in AdminDashboard.fxml.
     * It loads the StudentManagement.fxml file.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void openStudentManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/StudentManagement.fxml");
    }

    /**
     * Handles the logout functionality.
     * This method is triggered when the "Logout" button is clicked in AdminDashboard.fxml.
     * It loads the Login.fxml file, returning the user to the login screen.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/Login.fxml");
    }

    /**
     * Utility method to handle navigation between different screens.
     * It loads the specified FXML file and updates the current stage with the new scene.
     *
     * @param event The event triggered by clicking a button.
     * @param fxmlFile The FXML file to be loaded.
     * @throws IOException If the FXML file cannot be loaded.
     */
    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
