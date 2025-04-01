package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CourseManagementController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> courseCodeColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> sectionColumn;
    @FXML private TableColumn<Course, Integer> capacityColumn;
    @FXML private TableColumn<Course, String> lectureTimeColumn;
    @FXML private TableColumn<Course, String> finalExamColumn;
    @FXML private TableColumn<Course, String> locationColumn;

    @FXML private TextField studentIdField;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lectureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        courseTable.setItems(courseList);
        courseList.setAll(CourseDataStore.getCourses());
    }

    @FXML
    private void handleSearchByStudentId() {
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a student ID.");
            return;
        }

        ObservableList<Course> enrolledCourses = FXCollections.observableArrayList();
        for (Course course : CourseDataStore.getCourses()) {
            if (course.getEnrolledStudents().contains(studentId)) {
                enrolledCourses.add(course);
            }
        }

        if (enrolledCourses.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Courses Found", "No courses found for Student ID: " + studentId);
        } else {
            courseTable.setItems(enrolledCourses);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
