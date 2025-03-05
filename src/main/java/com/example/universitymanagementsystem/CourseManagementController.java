package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CourseManagementController {

    @FXML private TextField courseCodeField;
    @FXML private TextField courseNameField;
    @FXML private TextField sectionField;
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> courseCodeColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> sectionColumn;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        courseTable.setItems(courseList);

        // Preload sample data
        courseList.addAll(
                new Course("1", "Calculus I", "Section 1"),
                new Course("2", "Literature Basics", "Section 1"),
                new Course("2", "Literature Basics", "Section 2"),
                new Course("3", "Introduction to Programming", "Section 1"),
                new Course("4", "Introduction to Chemistry", "Section 1")
                // You can add more courses from your sample data here
        );
    }

    @FXML
    private void handleAddCourse() {
        String code = courseCodeField.getText().trim();
        String name = courseNameField.getText().trim();
        String section = sectionField.getText().trim();
        if (!code.isEmpty() && !name.isEmpty() && !section.isEmpty()) {
            courseList.add(new Course(code, name, section));
            courseCodeField.clear();
            courseNameField.clear();
            sectionField.clear();
        }
    }

    // Inner class representing a Course
    public static class Course {
        private final String code;
        private final String name;
        private final String section;

        public Course(String code, String name, String section) {
            this.code = code;
            this.name = name;
            this.section = section;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public String getSection() { return section; }
    }
}

