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
    private TableView<Course> coursesTableView;

    @FXML
    private TableColumn<Course, String> courseNameColumn;

    @FXML
    private TableColumn<Course, String> courseTimeColumn;

    @FXML
    private TableColumn<Course, Number> courseCapacityColumn;  // Number of students in the course

    @FXML
    private TableView<Event> eventsTableView;

    @FXML
    private TableColumn<Event, String> eventNameColumn;

    @FXML
    private TableColumn<Event, String> eventTimeColumn;

    @FXML
    private TableColumn<Event, Number> eventCapacityColumn;  // Number of registered students in the event

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Dashboard...");

        javafx.application.Platform.runLater(() -> {
            loadUpcomingCourses();
            loadRegisteredEvents();
        });
    }

    private void loadUpcomingCourses() {
        ObservableList<Course> upcomingCourses = FXCollections.observableArrayList(CourseDataStore.getCourses());

        System.out.println("Loaded Courses: " + upcomingCourses.size());

        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));

        // Bind the student count to the column using enrolledStudentCountProperty
        courseCapacityColumn.setCellValueFactory(course ->
                course.getValue().enrolledStudentCountProperty()
        );  // Show enrolled students count

        // Debug: Check if the enrolled student count is correctly updating
        for (Course course : upcomingCourses) {
            System.out.println("Course: " + course.getCourseName() + " | Enrolled Students: " + course.getEnrolledStudentCount());
        }

        coursesTableView.setItems(upcomingCourses);
        coursesTableView.refresh();
    }

    private void loadRegisteredEvents() {
        ObservableList<Event> registeredEvents = EventManagementController.getEventList();

        System.out.println("Loaded Events: " + registeredEvents.size());

        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        // Bind the registered student count property to the eventCapacityColumn
        eventCapacityColumn.setCellValueFactory(event ->
                event.getValue().registeredStudentCountProperty()
        );  // Show registered students count

        eventsTableView.setItems(registeredEvents);
        eventsTableView.refresh();
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}