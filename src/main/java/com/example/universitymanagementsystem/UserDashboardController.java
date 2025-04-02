package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @FXML
    private Label progressLabel;

    @FXML
    private TableView<Course> coursesTableView;

    @FXML
    private TableColumn<Course, String> courseNameColumn;

    @FXML
    private TableColumn<Course, String> courseTimeColumn;

    @FXML
    private TableView<?> eventsTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Dashboard...");

        // Debugging: Check if TableView and columns are not null
        System.out.println("coursesTableView: " + coursesTableView);
        System.out.println("courseNameColumn: " + courseNameColumn);
        System.out.println("courseTimeColumn: " + courseTimeColumn);

        javafx.application.Platform.runLater(this::loadUpcomingCourses);
    }

    private void loadProgress() {
        // Simulate loading progress from a data source, like a database or API
        progressLabel.setText("Progress: 80% Complete");
    }

    private void loadUpcomingCourses() {
        // Convert the list to an ObservableList that JavaFX can track
        ObservableList<Course> upcomingCourses = FXCollections.observableArrayList(CourseDataStore.getCourses());

        // Debug: Check if courses exist
        System.out.println("Loaded Courses: " + upcomingCourses.size());

        // Set the TableColumn properties
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));

        // Set the items to TableView
        coursesTableView.setItems(upcomingCourses);
        coursesTableView.refresh();  // 🔥 Forces UI refresh in case data isn't showing
    }


    private void loadRegisteredEvents() {
        // Simulate loading registered events from a data source
        // Example: eventsTableView.setItems(eventList);
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
