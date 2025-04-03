package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentController {

    @FXML private ListView<Course> availableCoursesListView;
    @FXML private ListView<String> enrolledCoursesListView;
    @FXML private Button enrollButton;
    @FXML private Button unenrollButton;
    @FXML private Button closeButton;

    private Student student;
    private ObservableList<Course> availableCourses;
    private ObservableList<String> enrolledCourses;
    private Stage stage;

    public void setStudent(Student student, Stage stage) {
        this.student = student;
        this.stage = stage;

        // Initialize course list with sections
        availableCourses = FXCollections.observableArrayList(getAvailableCourses());
        enrolledCourses = FXCollections.observableArrayList();

        // Populate enrolled courses list
        for (StudentCourse course : student.getEnrolledCourses()) {
            enrolledCourses.add(course.getCourseName() + " - " + course.getCredits() + " Credits");
        }

        availableCoursesListView.setItems(availableCourses);
        enrolledCoursesListView.setItems(enrolledCourses);
    }

    private List<Course> getAvailableCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("MATH001", "Calculus I", "MATH", "Section 1", 30, "Mon/Wed 9-11 AM", "Dec 10, 9 AM", "Room 101", "Dr. Smith"));
        courses.add(new Course("ENG101", "Literature Basics", "ENG", "Section 1", 25, "Tue/Thu 10-12 PM", "Dec 12, 10 AM", "Room 202", "Prof. Johnson"));
        courses.add(new Course("ENG101", "Literature Basics", "ENG", "Section 2", 25, "Mon/Wed 10-12 PM", "Dec 12, 10 AM", "Room 202", "Prof. Johnson"));
        courses.add(new Course("CS201", "Introduction to Programming", "CS", "Section 1", 42, "Tue/Thu 12-2 PM", "Dec 14, 12 PM", "Lab 3", "Dr. Lee"));
        courses.add(new Course("CHEM200", "Introduction to Chemistry", "CHEM", "Section 1", 50, "Mon/Thu 3-4 PM", "Dec 16, 3 PM", "Lab 5", "Dr. Patel"));
        courses.add(new Course("CHEM200", "Introduction to Chemistry", "CHEM", "Section 2", 50, "Mon/Tue 5-6 PM", "Dec 16, 3 PM", "Lab 5", "Dr. Patel"));
        courses.add(new Course("CHEM200", "Introduction to Chemistry", "CHEM", "Section 3", 50, "Fri/Thu 2-3 PM", "Dec 16, 3 PM", "Lab 5", "Dr. Patel"));
        courses.add(new Course("ENG101", "Introduction to French", "ENG", "Section 1", 25, "Tue/Thu 4:30-5:30 PM", "Dec 18, 4:30 PM", "Room 303", "Prof. Martin"));
        courses.add(new Course("ENG101", "Introduction to French", "ENG", "Section 2", 25, "Tue/Thu 5:30-6:30 PM", "Dec 18, 4:30 PM", "Room 303", "Prof. Martin"));
        courses.add(new Course("ENGG402", "Water Resources", "ENGG", "Section 1", 50, "Mon/Fri 9:00-10:30 AM", "Dec 20, 9 AM", "Room 410", "Dr. Kim"));
        return courses;
    }

    @FXML
    private void handleEnroll() {
        Course selectedCourse = availableCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            if (selectedCourse.getEnrolledStudents().size() < selectedCourse.getCapacity()) {
                selectedCourse.enrollStudent(student.getStudentId());
                student.enrollInCourse(new StudentCourse(selectedCourse.getCourseCode(), selectedCourse.getCourseName(), 3));
                enrolledCourses.add(selectedCourse.getCourseName() + " - " + selectedCourse.getSection());
            } else {
                showAlert("Enrollment Failed", "Course is full.");
            }
        }
    }

    @FXML
    private void handleUnenroll() {
        String selectedCourse = enrolledCoursesListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            enrolledCourses.remove(selectedCourse);
            student.unenrollFromCourse(selectedCourse);
        }
    }

    @FXML
    private void handleClose() {
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
