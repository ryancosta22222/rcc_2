package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SubjectManagementController {

    @FXML
    private TextField subjectCodeField;

    @FXML
    private TextField subjectNameField;

    @FXML
    private TableView<Subject> subjectTable;

    @FXML
    private TableColumn<Subject, String> codeColumn;

    @FXML
    private TableColumn<Subject, String> nameColumn;

    // ObservableList to hold subject data for the TableView
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind table columns to Subject properties
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectTable.setItems(subjectList);

        // Preload sample subject data
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

    @FXML
    private void handleAddSubject() {
        String code = subjectCodeField.getText().trim();
        String name = subjectNameField.getText().trim();
        if (!code.isEmpty() && !name.isEmpty()) {
            subjectList.add(new Subject(code, name));
            subjectCodeField.clear();
            subjectNameField.clear();
        }
    }

    // Inner class representing a Subject.
    // You can later move this class to its own file if desired.
    public static class Subject {
        private final String code;
        private final String name;

        public Subject(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
