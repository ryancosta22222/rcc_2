package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the Course Management module.
 * This class handles adding and displaying courses in the system.
 */
public class CourseManagementController {

    // TableView to display courses in the UI.
    @FXML private TableView<Course> courseTable;

    // TableColumn elements linked to FXML to show course details.
    @FXML private TableColumn<Course, String> courseCodeColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> sectionColumn;

    // Input fields for adding new courses (connected to FXML).
    @FXML private TextField courseCodeField;
    @FXML private TextField courseNameField;
    @FXML private TextField sectionField;

    // ObservableList to store and manage course data dynamically.
    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    /**
     * Initializes the Course Management Controller.
     * This method is automatically called when the corresponding FXML file is loaded.
     * It sets up column bindings and populates the course table with sample data.
     */
    @FXML
    private void initialize() {
        // Binds table columns to specific properties of the Course object.
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));

        // Sets the TableView to use the ObservableList for displaying courses.
        courseTable.setItems(courseList);

        // Preloads some sample courses into the list for demonstration purposes.
        courseList.addAll(
                new Course("1", "Calculus I", "Section 1"),
                new Course("2", "Literature Basics", "Section 1"),
                new Course("2", "Literature Basics", "Section 2"),
                new Course("3", "Introduction to Programming", "Section 1"),
                new Course("4", "Introduction to Chemistry", "Section 1")
        );
    }

    /**
     * Handles the addition of a new course.
     * This method is triggered when the "Add Course" button is clicked in the FXML file.
     * It retrieves input values, validates them, and adds a new course to the list.
     */
    @FXML
    private void handleAddCourse() {
        // Retrieves user input from text fields.
        String code = courseCodeField.getText().trim();
        String name = courseNameField.getText().trim();
        String section = sectionField.getText().trim();

        // Ensures no empty fields before adding a new course.
        if (!code.isEmpty() && !name.isEmpty() && !section.isEmpty()) {
            // Adds a new course to the list and updates the TableView.
            courseList.add(new Course(code, name, section));

            // Clears input fields after adding a course.
            courseCodeField.clear();
            courseNameField.clear();
            sectionField.clear();
        }
    }

    /**
     * Inner class representing a Course.
     * This class is used to store and retrieve course data.
     */
    public static class Course {
        private final String code;
        private final String name;
        private final String section;

        /**
         * Constructor for creating a new Course object.
         * @param code Unique identifier for the course.
         * @param name Name of the course.
         * @param section Section number of the course.
         */
        public Course(String code, String name, String section) {
            this.code = code;
            this.name = name;
            this.section = section;
        }

        // Getter methods to retrieve course details (used by TableView).
        public String getCode() { return code; }
        public String getName() { return name; }
        public String getSection() { return section; }
    }
}
