package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentProfileController {

    @FXML private Label studentIdLabel;
    @FXML private Label studentNameLabel;
    @FXML private Label studentEmailLabel;
    @FXML private Label studentAcademicLevelLabel;
    @FXML private Label studentSemesterLabel;

    private Stage stage;

    public void setStudentData(Student student, Stage stage) {
        this.stage = stage;
        studentIdLabel.setText(student.getStudentId());
        studentNameLabel.setText(student.getName());
        studentEmailLabel.setText(student.getEmail());
        studentAcademicLevelLabel.setText(student.getAcademicLevel());
        studentSemesterLabel.setText(student.getCurrentSemester());
    }

    @FXML
    private void handleClose() {
        stage.close(); // Close the window
    }
}
