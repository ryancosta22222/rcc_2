package com.example.universitymanagementsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class StudentHomeController {

    @FXML
    private TextField studentIdInput;
    @FXML
    private Button searchBtn;

    private Student student;

    @FXML
    private void handleSearchStudent() {
        String enteredId = studentIdInput.getText().trim();
        if (enteredId.isEmpty()) {
            showAlert("Validation Error", "Please enter a Student ID.");
            return;
        }

        ObservableList<Student> studentList = StudentManagementController.getStudentList();
        for (Student s : studentList) {
            if (s.getStudentId().equalsIgnoreCase(enteredId)) {
                student = s;
                showAlert("Success", "Student data loaded for ID: " + enteredId);
                return;
            }
        }

        showAlert("Not Found", "No student found with ID: " + enteredId);
    }

    @FXML
    private void handleViewProfile() {
        if (student == null) {
            showAlert("Error", "Please search and load a student first.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Profile");
        alert.setHeaderText("Personal Information");
        alert.setContentText("Name: " + student.getName() + "\nEmail: " + student.getEmail() +
                "\nLevel: " + student.getAcademicLevel() + "\nSemester: " + student.getCurrentSemester() +
                "\nProfile Image Path: " + (student.getProfileImagePath() != null ? student.getProfileImagePath() : "Not set"));
        alert.showAndWait();
    }

    @FXML
    private void handleEditProfile() {
        if (student == null) {
            showAlert("Error", "Please search and load a student first.");
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update Password and Profile Picture");

        Label passwordLabel = new Label("New Password:");
        PasswordField passwordField = new PasswordField();

        Label imageLabel = new Label("Profile Image:");
        Button browseButton = new Button("Choose Image");

        FileChooser fileChooser = new FileChooser();
        browseButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                student.setProfileImagePath(file.getAbsolutePath());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(passwordLabel, 0, 0);
        grid.add(passwordField, 1, 0);
        grid.add(imageLabel, 0, 1);
        grid.add(browseButton, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String newPassword = passwordField.getText().trim();
                if (!newPassword.isEmpty()) {
                    student.setPassword(newPassword);
                    showAlert("Success", "Password updated successfully.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    private void handleViewCourses() {
        if (student == null) {
            showAlert("Error", "Please search and load a student first.");
            return;
        }
        StringBuilder enrolled = new StringBuilder("Enrolled Courses:\n");
        for (StudentCourse course : student.getEnrolledCourses()) {
            enrolled.append(course.getCourseId()).append(" - ").append(course.getCourseName()).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Courses");
        alert.setHeaderText(null);
        alert.setContentText(enrolled.toString());
        alert.showAndWait();
    }

    @FXML
    private void handleViewGrades() {
        if (student == null) {
            showAlert("Error", "Please search and load a student first.");
            return;
        }
        StringBuilder gradesText = new StringBuilder("Grades:\n");
        for (Grade grade : student.getGrades()) {
            gradesText.append(grade.getCourse()).append(": ").append(grade.getGrade()).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Grades");
        alert.setHeaderText(null);
        alert.setContentText(gradesText.toString());
        alert.showAndWait();
    }

    @FXML
    private void handleViewTuition() {
        if (student == null) {
            showAlert("Error", "Please search and load a student first.");
            return;
        }
        String tuition;
        switch (student.getAcademicLevel().toLowerCase()) {
            case "undergraduate": tuition = "$5000"; break;
            case "graduate": tuition = "$4000"; break;
            default: tuition = "N/A";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tuition");
        alert.setHeaderText("Tuition Status");
        alert.setContentText("Academic Level: " + student.getAcademicLevel() + "\nTuition: " + tuition);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
