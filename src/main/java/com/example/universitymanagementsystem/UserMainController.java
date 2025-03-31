package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class UserMainController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button dashboardBtn, courseMgmtBtn, eventMgmtBtn, profileBtn, facultyBtn, subjectsBtn, logoutBtn;

    @FXML
    private void initialize() {
        // Load the default view (User Dashboard) into the center area.
        loadCenter("/fxml/UserDashboard.fxml");
    }

    @FXML
    private void loadDashboard(ActionEvent event) {
        loadCenter("/fxml/UserDashboard.fxml");
    }

    @FXML
    private void loadCourseManagement(ActionEvent event) {
        // Load the student-specific course management view.
        loadCenter("/fxml/CourseManagementStudent.fxml");
    }

    @FXML
    private void loadEventManagement(ActionEvent event) {
        loadCenter("/fxml/UserEvents.fxml");
    }

    @FXML
    private void loadProfile(ActionEvent event) {
        loadCenter("/fxml/UserProfile.fxml");
    }

    // Loads Faculty Profiles view (for students)
    @FXML
    private void loadFacultyProfiles(ActionEvent event) {
        loadCenter("/fxml/FacultyList.fxml");
    }

    // NEW: Loads the Subjects view (for students)
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

    // Helper method to load an FXML file into the center of the BorderPane.
    private void loadCenter(String fxmlPath) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainLayout.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
