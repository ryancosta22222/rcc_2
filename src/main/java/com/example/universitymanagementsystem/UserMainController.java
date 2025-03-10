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
    private Button dashboardBtn, courseMgmtBtn, eventMgmtBtn, profileBtn, logoutBtn;

    @FXML
    private void initialize() {
        // Load default view: User Dashboard.
        loadCenter("/fxml/UserDashboard.fxml");
    }

    @FXML
    private void loadDashboard(ActionEvent event) {
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
    private void handleLogout(ActionEvent event) {
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(login));
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void loadCenter(String fxmlPath) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainLayout.setCenter(node);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
