package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    @FXML private AnchorPane courseEnrollmentPane;
    @FXML private ListView<String> availableCoursesListView;

    private Student student;
    private Stage profileStage;
    private ObservableList<StudentCourse> availableCourses = FXCollections.observableArrayList();

    public void setStudentData(Student student, Stage profileStage) {
        this.student = student;
        this.profileStage = profileStage;

        // Set student information
        studentIdLabel.setText(student.getStudentId());
        studentNameLabel.setText(student.getName());
        studentEmailLabel.setText(student.getEmail());
        studentAcademicLevelLabel.setText(student.getAcademicLevel());
        studentSemesterLabel.setText(student.getCurrentSemester());
        gpaLabel.setText(String.format("%.2f", student.getGpa()));
        graduationStatusLabel.setText(student.getGraduationStatus());
        completedCreditsLabel.setText("Completed Credits: " + getCompletedCredits());
        remainingCreditsLabel.setText("Remaining Credits: " + getRemainingCredits());

        // Sample available courses (in real case, fetch from backend or database)
        availableCourses.addAll(
                new StudentCourse("CS101", "Introduction to Programming", 3),
                new StudentCourse("CS102", "Data Structures", 3),
                new StudentCourse("MATH101", "Calculus I", 4),
                new StudentCourse("PHYS101", "Physics I", 4)
        );

        updateCourseListView();
    }

    private void updateCourseListView() {
        availableCoursesListView.getItems().clear();
        for (StudentCourse course : availableCourses) {
            availableCoursesListView.getItems().add(course.getCourseId() + " - " + course.getCourseName());
        }
    }

    private int getCompletedCredits() {
        int total = 0;
        for (StudentCourse course : student.getEnrolledCourses()) {
            if (course.isCompleted()) {
                total += course.getCredits();
            }
        }
        return total;
    }

    private int getRemainingCredits() {
        int required = student.getAcademicLevel().equalsIgnoreCase("undergraduate") ? 120 : 60;
        return required - getCompletedCredits();
    }

    @FXML
    private void handleManageCourses() {
        courseEnrollmentPane.setVisible(!courseEnrollmentPane.isVisible());
    }

    @FXML
    private void handleEnrollInCourse() {
        String selectedCourse = availableCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String courseId = selectedCourse.split(" - ")[0];
            StudentCourse courseToEnroll = availableCourses.stream()
                    .filter(c -> c.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);

            if (courseToEnroll != null && !student.getEnrolledCourses().contains(courseToEnroll)) {
                student.enrollInCourse(courseToEnroll);
                showAlert("Enrollment Successful", "Student enrolled in: " + selectedCourse);
            } else {
                showAlert("Already Enrolled", "Student is already enrolled in: " + selectedCourse);
            }
        }
    }

    @FXML
    private void handleUnenrollFromCourse() {
        String selectedCourse = availableCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String courseId = selectedCourse.split(" - ")[0];
            student.unenrollFromCourse(courseId);
            showAlert("Unenrollment Successful", "Student unenrolled from: " + selectedCourse);
        }
    }

    @FXML
    private void handleClose() {
        profileStage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
