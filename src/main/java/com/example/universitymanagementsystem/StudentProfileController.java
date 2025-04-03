package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

public class StudentProfileController {

    @FXML private Label studentIdLabel;
    @FXML private Label studentNameLabel;
    @FXML private Label studentEmailLabel;
    @FXML private Label studentAcademicLevelLabel;
    @FXML private Label studentSemesterLabel;
    @FXML private Label gpaLabel;
    @FXML private Label graduationStatusLabel;
    @FXML private Label completedCreditsLabel;
    @FXML private Label remainingCreditsLabel;
    @FXML private AnchorPane courseEnrollmentPane;  // The course enrollment pane
    @FXML private ListView<String> availableCoursesListView;  // ListView to display courses

    private Stage stage;
    private Student student;

    public void setStudentData(Student student, Stage stage) {
        this.student = student;
        this.stage = stage;

        // Set student information
        studentIdLabel.setText(student.getStudentId());
        studentNameLabel.setText(student.getName());
        studentEmailLabel.setText(student.getEmail());
        studentAcademicLevelLabel.setText(student.getAcademicLevel());
        studentSemesterLabel.setText(student.getCurrentSemester());
        gpaLabel.setText(String.format("%.2f", student.getGpa()));
        graduationStatusLabel.setText(student.getGraduationStatus());

        // Update credits information
        updateCredits(student);

        // Populate the available courses ListView
        populateAvailableCourses();
    }

    private void updateCredits(Student student) {
        int completedCredits = 0;
        int requiredCredits = student.getAcademicLevel().equalsIgnoreCase("undergraduate") ? 120 : 60;

        // Loop through enrolled courses and calculate completed credits
        for (StudentCourse course : student.getEnrolledCourses()) {
            if (course.isCompleted()) {
                completedCredits += course.getCredits();
            }
        }

        // Set the labels
        completedCreditsLabel.setText("Completed Credits: " + completedCredits);
        remainingCreditsLabel.setText("Remaining Credits: " + Math.max(0, requiredCredits - completedCredits));
    }

    // Populate the available courses ListView (For demo purposes, we'll use dummy courses)
    private void populateAvailableCourses() {
        ObservableList<String> availableCourses = FXCollections.observableArrayList(
                "CS101 - Introduction to Programming",
                "CS102 - Data Structures",
                "MATH101 - Calculus I",
                "PHYS101 - Physics I"
        );

        availableCoursesListView.setItems(availableCourses);
    }

    // Handle the "Manage Courses" button click
    @FXML
    private void handleManageCourses() {
        // Toggle the visibility of the course enrollment pane
        boolean isVisible = courseEnrollmentPane.isVisible();
        courseEnrollmentPane.setVisible(!isVisible);
    }


    // Handle Enroll in Course
    @FXML
    private void handleEnrollInCourse() {
        String selectedCourse = availableCoursesListView.getSelectionModel().getSelectedItem();

        if (selectedCourse != null && !student.getEnrolledCourses().contains(selectedCourse)) {
            // Example: Using a fixed course name and credits (replace with dynamic data if necessary)
            StudentCourse course = new StudentCourse(selectedCourse, selectedCourse, 3);  // Using 3 credits for simplicity
            student.enrollInCourse(course);  // Add course to student's enrolled courses
            updateCredits(student);  // Update credits info
            populateAvailableCourses();  // Refresh available courses list
        } else {
            showAlert("Error", "Already Enrolled or No Course Selected");
        }
    }


    // Handle Unenroll from Course
    @FXML
    private void handleUnenrollFromCourse() {
        String selectedCourse = availableCoursesListView.getSelectionModel().getSelectedItem();

        if (selectedCourse != null && student.getEnrolledCourses().contains(selectedCourse)) {
            student.unenrollFromCourse(selectedCourse);  // Remove course from student's enrolled courses
            updateCredits(student);  // Update credits info
            populateAvailableCourses();  // Refresh available courses list
        } else {
            showAlert("Error", "Not Enrolled in This Course or No Course Selected");
        }
    }

    // Show alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
}
