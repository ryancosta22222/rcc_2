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

public class AdminMainController {

    @FXML
    private BorderPane mainLayout;  // The outer layout from AdminMainLayout.fxml

    @FXML
    private Button dashboardBtn, subjectMgmtBtn, courseMgmtBtn, studentMgmtBtn, eventMgmtBtn, logoutBtn;

    @FXML
    private void initialize() {
        // Load default view: Admin Dashboard.
        loadCenter("/fxml/AdminDashboard.fxml");
    }

    @FXML
    private void loadDashboard(ActionEvent event) {
        loadCenter("/fxml/AdminDashboard.fxml");
    }

    @FXML
    private void loadSubjectManagement(ActionEvent event) {
        loadCenter("/fxml/SubjectManagement.fxml");
    }

    @FXML
    private void loadCourseManagement(ActionEvent event) {
        loadCenter("/fxml/CourseManagement.fxml");
    }

    @FXML
    private void loadStudentManagement(ActionEvent event) {
        loadCenter("/fxml/StudentManagement.fxml");
    }

    @FXML
    private void loadEventManagement(ActionEvent event) {
        loadCenter("/fxml/EventManagement.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
