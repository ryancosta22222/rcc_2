package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class UserMainController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private void initialize() {
        // Load the default view (User Dashboard) into the center area.
        loadCenter("/fxml/UserDashboard.fxml");
    }

    @FXML
    private void loadDashboard(ActionEvent event) {
        System.out.println("Loading Dashboard...");
        loadCenter("/fxml/UserDashboard.fxml");
    }

    @FXML
    private void loadCourseManagement(ActionEvent event) {
        loadCenter("/fxml/CourseManagement.fxml");
    }

    @FXML
    private void loadEventManagement(ActionEvent event) {
        loadCenter("/fxml/UserEvents.fxml");
    }

    @FXML
    private void loadProfile(ActionEvent event) {
        loadCenter("/fxml/UserProfile.fxml");
    }

    @FXML
    private void loadFacultyProfiles(ActionEvent event) {
        loadCenter("/fxml/FacultyList.fxml");
    }

    @FXML
    private void loadSubjects(ActionEvent event) {
        loadCenter("/fxml/StudentSubject.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(login));
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCenter(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }
            Node node = loader.load();
            mainLayout.setCenter(node);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }

    // This method is now used by the sidebar "View Courses" button
    @FXML
    public void openCourseManagement(ActionEvent event) throws IOException {
        loadCenter("/fxml/CourseManagement.fxml");
    }
}
