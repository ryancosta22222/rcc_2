package com.example.universitymanagementsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CourseManagementStudentController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> courseCodeColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> subjectCodeColumn;
    @FXML private TableColumn<Course, String> sectionColumn;
    @FXML private TableColumn<Course, String> teacherNameColumn;
    @FXML private TableColumn<Course, Integer> capacityColumn;
    @FXML private TableColumn<Course, String> lectureTimeColumn;
    @FXML private TableColumn<Course, String> finalExamColumn;
    @FXML private TableColumn<Course, String> locationColumn;

    @FXML private Button viewMyEnrollmentsBtn;

    // Use the shared course list from CourseDataStore.
    private ObservableList<Course> courseList = CourseDataStore.getCourses();

    @FXML
    private void initialize() {
        // Set up table columns.
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lectureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        courseTable.setItems(courseList);

        // When a course is selected, show its detailed information.
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Course: ").append(newSelection.getCourseName()).append("\n")
                        .append("Code: ").append(newSelection.getCourseCode()).append("\n")
                        .append("Subject: ").append(newSelection.getSubjectCode()).append("\n")
                        .append("Section: ").append(newSelection.getSection()).append("\n")
                        .append("Teacher: ").append(newSelection.getTeacherName()).append("\n")
                        .append("Capacity: ").append(newSelection.getCapacity()).append("\n")
                        .append("Lecture Time: ").append(newSelection.getLectureTime()).append("\n")
                        .append("Final Exam: ").append(newSelection.getFinalExamDateTime()).append("\n")
                        .append("Location: ").append(newSelection.getLocation()).append("\n")
                        .append("Enrolled Students: ")
                        .append(newSelection.getEnrolledStudents().isEmpty() ? "None" : String.join(", ", newSelection.getEnrolledStudents()));
                showAlert(Alert.AlertType.INFORMATION, "Course Details", sb.toString());
            }
        });
    }

    @FXML
    private void handleViewMyEnrollments() {
        User currentUser = Session.getInstance().getUser();
        if(currentUser == null || !currentUser.getRole().equalsIgnoreCase("STUDENT")){
            showAlert(Alert.AlertType.WARNING, "Access Denied", "Only students can view enrollments.");
            return;
        }
        String username = currentUser.getUsername();
        StringBuilder sb = new StringBuilder();
        for(Course c : courseList) {
            if(c.getEnrolledStudents().contains(username)) {
                sb.append(c.getCourseName()).append(" (").append(c.getCourseCode()).append(")\n");
            }
        }
        String enrolledCourses = sb.length() > 0 ? sb.toString() : "You are not enrolled in any courses.";
        showAlert(Alert.AlertType.INFORMATION, "My Enrollments", enrolledCourses);
    }

    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
