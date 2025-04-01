package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @FXML
    private Label progressLabel;

    @FXML
    private TableView<?> coursesTableView;

    @FXML
    private TableView<?> eventsTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProgress();
        loadUpcomingCourses();
        loadRegisteredEvents();
    }

    private void loadProgress() {
        // Simulate loading progress from a data source, like a database or API
        // This is just an example. Replace with actual data retrieval logic.
        progressLabel.setText("Progress: 80% Complete");
    }

    private void loadUpcomingCourses() {
        // Simulate loading upcoming courses from a data source
        // Here you can add actual data to the TableView using TableView.setItems().
        // Example:
        // coursesTableView.setItems(courseList);
    }

    private void loadRegisteredEvents() {
        // Simulate loading registered events from a data source
        // Example:
        // eventsTableView.setItems(eventList);
    }

    @FXML
    private void openCourseManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/CourseManagement.fxml");
    }

    @FXML
    private void openEventManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/UserEvents.fxml");
    }

    @FXML
    public void openSubjects(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/StudentSubject.fxml");
    }

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}