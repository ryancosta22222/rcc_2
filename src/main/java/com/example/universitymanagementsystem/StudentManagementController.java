package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentManagementController {

    @FXML private TextField studentIdField;
    @FXML private TextField studentNameField;
    @FXML private TextField studentEmailField;
    @FXML private TextField studentAcademicLevelField;
    @FXML private TextField studentSemesterField;

    // New TextField to show tuition fee
    @FXML private TextField tuitionField;

    @FXML private Button addStudentBtn;
    @FXML private Button editStudentBtn;
    @FXML private Button deleteStudentBtn;
    @FXML private Button manageTuitionBtn; // Added button to manage tuition fees

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> studentIdColumn;
    @FXML private TableColumn<Student, String> studentNameColumn;
    @FXML private TableColumn<Student, String> studentEmailColumn;
    @FXML private TableColumn<Student, String> studentAcademicLevelColumn;
    @FXML private TableColumn<Student, String> studentSemesterColumn;

    private static ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Student selectedStudent = null;

    @FXML
    private void initialize() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentAcademicLevelColumn.setCellValueFactory(new PropertyValueFactory<>("academicLevel"));
        studentSemesterColumn.setCellValueFactory(new PropertyValueFactory<>("currentSemester"));

        studentTable.setItems(studentList);

        // Populate fields when a student is selected
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedStudent = newSelection;
            if (newSelection != null) {
                studentIdField.setText(newSelection.getStudentId());
                studentNameField.setText(newSelection.getName());
                studentEmailField.setText(newSelection.getEmail());
                studentAcademicLevelField.setText(newSelection.getAcademicLevel());
                studentSemesterField.setText(newSelection.getCurrentSemester());
            }
        });

        // Disable editing controls for students
        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && currentUser.getRole() != null && currentUser.getRole().equalsIgnoreCase("STUDENT")) {
            addStudentBtn.setDisable(true);
            editStudentBtn.setDisable(true);
            deleteStudentBtn.setDisable(true);
            studentIdField.setDisable(true);
            studentNameField.setDisable(true);
            studentEmailField.setDisable(true);
            studentAcademicLevelField.setDisable(true);
            studentSemesterField.setDisable(true);
        }
    }

    @FXML
    private void handleAddStudent() {
        String studentId = studentIdField.getText().trim();
        String name = studentNameField.getText().trim();
        String email = studentEmailField.getText().trim();
        String academicLevel = studentAcademicLevelField.getText().trim();
        String currentSemester = studentSemesterField.getText().trim();

        if (studentId.isEmpty() || name.isEmpty() || email.isEmpty() || academicLevel.isEmpty() || currentSemester.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        // Check for unique student ID
        for (Student s : studentList) {
            if (s.getStudentId().equals(studentId)) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Student ID must be unique.");
                return;
            }
        }

        Student newStudent = new Student("defaultUsername", "defaultPassword", studentId, name, email, academicLevel, currentSemester);
        studentList.add(newStudent); // Add the new student to the list
        clearInputFields();
    }

    @FXML
    private void handleEditStudent() {
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to edit.");
            return;
        }

        String studentId = studentIdField.getText().trim();
        String name = studentNameField.getText().trim();
        String email = studentEmailField.getText().trim();
        String academicLevel = studentAcademicLevelField.getText().trim();
        String currentSemester = studentSemesterField.getText().trim();

        if (studentId.isEmpty() || name.isEmpty() || email.isEmpty() || academicLevel.isEmpty() || currentSemester.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        int index = studentList.indexOf(selectedStudent);
        if (index >= 0) {
            studentList.set(index, new Student(selectedStudent.getUsername(), selectedStudent.getPassword(), studentId, name, email, academicLevel, currentSemester));
        }
    }

    @FXML
    private void handleDeleteStudent() {
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to delete.");
            return;
        }
        studentList.remove(selectedStudent);
        selectedStudent = null;
        clearInputFields();
    }

    // New method to handle Tuition Fee management
    @FXML
    private void handleManageTuition(ActionEvent event) {
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to manage tuition.");
            return;
        }

        String academicLevel = selectedStudent.getAcademicLevel().trim();
        if (academicLevel.equalsIgnoreCase("undergraduate")) {
            tuitionField.setText("$5000");
        } else if (academicLevel.equalsIgnoreCase("graduate")) {
            tuitionField.setText("$4000");
        } else {
            tuitionField.setText("N/A");
        }
    }

    private void clearInputFields() {
        studentIdField.clear();
        studentNameField.clear();
        studentEmailField.clear();
        studentAcademicLevelField.clear();
        studentSemesterField.clear();
        studentTable.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Public static getter to allow other modules to access the student list
    public static ObservableList<Student> getStudentList() {
        return studentList;
    }
    @FXML
    private void handleViewProfile() {
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to view their profile.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentProfile.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the student data
            StudentProfileController controller = loader.getController();
            Stage profileStage = new Stage();
            controller.setStudentData(selectedStudent, profileStage);

            // Show the profile window
            profileStage.setScene(new Scene(root));
            profileStage.setTitle("Student Profile");
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open student profile.");
        }
    }


}

