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
    // Additional columns can be added as needed.

    @FXML private TextField courseCodeField;
    @FXML private TextField courseNameField;
    @FXML private TextField subjectCodeField;
    @FXML private TextField sectionField;
    @FXML private TextField capacityField;
    @FXML private TextField lectureTimeField;
    @FXML private TextField finalExamField;
    @FXML private TextField locationField;
    @FXML private TextField teacherField;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));

        courseTable.setItems(courseList);

        // --- Sample Course Data from UMS ---
        courseList.addAll(
                new Course("1", "Calculus I", "MATH001", "Section 1", 30, "Mon/Wed 9-11 AM", "12/15/2025 9:00", "Room 101", "Dr. Alan Turing"),
                new Course("2", "Literature Basics", "ENG101", "Section 1", 25, "Tue/Thu 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"),
                new Course("2", "Literature Basics", "ENG101", "Section 2", 25, "Mon/Wed 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"),
                new Course("3", "Introduction to Programming", "CS201", "Section 1", 42, "Tue/Thu 12-2 PM", "12/16/2025 12:30", "Room 103", "Prof. Bahar Nozari"),
                new Course("4", "Introduction to Chemistry", "CHEM200", "Section 1", 50, "Mon/Thu 3-4 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"),
                new Course("4", "Introduction to Chemistry", "CHEM200", "Section 2", 50, "Mon/Tue 5-6 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"),
                new Course("4", "Introduction to Chemistry", "CHEM200", "Section 3", 50, "Fri/Thu 2-3 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"),
                new Course("5", "Introduction to French", "ENG101", "Section 1", 25, "Tue/Thur 4:30-5:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"),
                new Course("5", "Introduction to French", "ENG101", "Section 2", 25, "Tue/Thur 5:30-6:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"),
                new Course("6", "Water Resources", "ENGG402", "Section 1", 50, "Mon/Fri 9:00-10:30", "12/01/2025 9:00", "Room 203", "Dr. Albozr Gharabaghi")
        );
    }

    @FXML
    private void handleAddCourse() {
        try {
            String code = courseCodeField.getText().trim();
            String name = courseNameField.getText().trim();
            String subj = subjectCodeField.getText().trim();
            String section = sectionField.getText().trim();
            int cap = Integer.parseInt(capacityField.getText().trim());
            String lecture = lectureTimeField.getText().trim();
            String exam = finalExamField.getText().trim();
            String loc = locationField.getText().trim();
            String teacher = teacherField.getText().trim();

            if (code.isEmpty() || name.isEmpty() || subj.isEmpty() || section.isEmpty() || lecture.isEmpty() ||
                    exam.isEmpty() || loc.isEmpty() || teacher.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            courseList.add(new Course(code, name, subj, section, cap, lecture, exam, loc, teacher));

            // Clear input fields.
            courseCodeField.clear();
            courseNameField.clear();
            subjectCodeField.clear();
            sectionField.clear();
            capacityField.clear();
            lectureTimeField.clear();
            finalExamField.clear();
            locationField.clear();
            teacherField.clear();
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Capacity");
            alert.setContentText("Capacity must be a number.");
            alert.showAndWait();
        } catch(IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Missing Data");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
