package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;

import java.io.IOException;

public class SubjectManagementController {

    @FXML private TextField subjectCodeField;
    @FXML private TextField subjectNameField;
    @FXML private TextField subjectSearchField; // For filtering subjects
    @FXML private Button addSubjectBtn;         // Button for adding a subject
    @FXML private Button editSubjectBtn;        // Button for editing a subject
    @FXML private Button deleteSubjectBtn;      // Button for deleting a subject
    private Student selectedStudent = null;


    @FXML private TableView<Subject> subjectTable;
    @FXML private TableColumn<Subject, String> codeColumn;
    @FXML private TableColumn<Subject, String> nameColumn;

    // Static subject list shared by both admin and student modules
    private static ObservableList<Subject> subjectList = FXCollections.observableArrayList();

    // Track the selected subject (for editing/deleting)
    private Subject selectedSubject = null;

    @FXML
    private void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Initialize sample subjects if the list is empty
        if (subjectList.isEmpty()) {
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

        // Set up search functionality
        FilteredList<Subject> filteredData = new FilteredList<>(subjectList, p -> true);
        subjectSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(subject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return subject.getCode().toLowerCase().contains(lowerCaseFilter) ||
                        subject.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Subject> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(subjectTable.comparatorProperty());
        subjectTable.setItems(sortedData);

        // Populate fields when a subject is selected
        subjectTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedSubject = newSelection;
            if (newSelection != null) {
                subjectCodeField.setText(newSelection.getCode());
                subjectNameField.setText(newSelection.getName());
            }
        });

        // Disable editing controls for students
        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && currentUser.getRole().equalsIgnoreCase("STUDENT")) {
            addSubjectBtn.setDisable(true);
            editSubjectBtn.setDisable(true);
            deleteSubjectBtn.setDisable(true);
            subjectCodeField.setDisable(true);
            subjectNameField.setDisable(true);
        }
    }

    @FXML
    private void handleAddSubject() {
        String code = subjectCodeField.getText().trim();
        String name = subjectNameField.getText().trim();

        if (code.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Subject Code and Name cannot be empty.");
            return;
        }

        // Ensure unique subject code
        for (Subject s : subjectList) {
            if (s.getCode().equalsIgnoreCase(code)) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Subject code must be unique.");
                return;
            }
        }

        Subject newSubject = new Subject(code, name);
        subjectList.add(newSubject);
        clearInputFields();
    }

    @FXML
    private void handleEditSubject() {
        if (selectedSubject == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a subject to edit.");
            return;
        }
        String code = subjectCodeField.getText().trim();
        String name = subjectNameField.getText().trim();

        if (code.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Subject Code and Name cannot be empty.");
            return;
        }

        // Check uniqueness if subject code has changed
        if (!selectedSubject.getCode().equalsIgnoreCase(code)) {
            for (Subject s : subjectList) {
                if (s.getCode().equalsIgnoreCase(code)) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Subject code must be unique.");
                    return;
                }
            }
        }

        int index = subjectList.indexOf(selectedSubject);
        if (index >= 0) {
            subjectList.set(index, new Subject(code, name));
            selectedSubject = null;
            clearInputFields();
        }
    }


    @FXML
    private void handleDeleteSubject() {
        if (selectedSubject == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a subject to delete.");
            return;
        }
        subjectList.remove(selectedSubject);
        selectedSubject = null;
        clearInputFields();
    }

    private void clearInputFields() {
        subjectCodeField.clear();
        subjectNameField.clear();
        subjectTable.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Public static getter so other modules (like student view) can access the subject list
    public static ObservableList<Subject> getSubjectList() {
        return subjectList;
    }

    // Inner class representing a subject record
    public static class Subject {
        private final String code;
        private final String name;

        public Subject(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
    }

}
