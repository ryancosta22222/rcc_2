package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for managing subjects in the University Management System.
 * This class handles interactions between the UI and subject-related data.
 */
public class SubjectManagementController {

    // TextField for inputting the subject code
    @FXML
    private TextField subjectCodeField;

    // TextField for inputting the subject name
    @FXML
    private TextField subjectNameField;

    // TableView for displaying the list of subjects
    @FXML
    private TableView<Subject> subjectTable;

    // TableColumn for displaying subject codes in the TableView
    @FXML
    private TableColumn<Subject, String> codeColumn;

    // TableColumn for displaying subject names in the TableView
    @FXML
    private TableColumn<Subject, String> nameColumn;

    // ObservableList to hold subject data for the TableView
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();

    /**
     * Initializes the controller after the FXML file is loaded.
     * This method sets up the TableView columns and preloads sample subject data.
     */
    @FXML
    private void initialize() {
        // Bind table columns to Subject properties so they display the correct data
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Set the ObservableList as the data source for the TableView
        subjectTable.setItems(subjectList);

        // Preload sample subject data to display when the UI loads
        subjectList.addAll(
                new Subject("MATH001", "Mathematics"),
                new Subject("ENG101", "English"),
                new Subject("CS201", "Computer Science"),
                new Subject("CHEM200", "Chemistry"),
                new Subject("BIO300", "Biology"),
                new Subject("ENGG402", "Engineering"),
                new Subject("HIST101", "History"),
                new Subject("MUSIC102", "Music"),
                new Subject("PSYCHO100", "Psychology")
        );
    }

    /**
     * Handles adding a new subject to the TableView when the user enters data and clicks the "Add Subject" button.
     */
    @FXML
    private void handleAddSubject() {
        // Get input values from text fields
        String code = subjectCodeField.getText().trim();
        String name = subjectNameField.getText().trim();

        // Validate input: Ensure both fields are not empty before adding to the list
        if (!code.isEmpty() && !name.isEmpty()) {
            // Add the new subject to the ObservableList (which updates the TableView automatically)
            subjectList.add(new Subject(code, name));

            // Clear the text fields after adding a new subject
            subjectCodeField.clear();
            subjectNameField.clear();
        }
    }

    /**
     * Inner class representing a Subject.
     * This class holds subject details such as the subject code and name.
     *
     * This class is currently inside the controller but can be moved to a separate file if needed.
     */
    public static class Subject {
        private final String code;
        private final String name;

        // Constructor to initialize subject properties
        public Subject(String code, String name) {
            this.code = code;
            this.name = name;
        }

        // Getter for subject code (used by TableView)
        public String getCode() {
            return code;
        }

        // Getter for subject name (used by TableView)
        public String getName() {
            return name;
        }
    }
}
