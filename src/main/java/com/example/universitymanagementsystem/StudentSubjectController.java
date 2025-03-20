package com.example.universitymanagementsystem;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentSubjectController {

    @FXML private TextField subjectSearchField;
    @FXML private TableView<SubjectManagementController.Subject> subjectTable;
    @FXML private TableColumn<SubjectManagementController.Subject, String> codeColumn;
    @FXML private TableColumn<SubjectManagementController.Subject, String> nameColumn;

    @FXML
    private void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Get the shared subject list from the admin module
        FilteredList<SubjectManagementController.Subject> filteredData =
                new FilteredList<>(SubjectManagementController.getSubjectList(), p -> true);

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

        SortedList<SubjectManagementController.Subject> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(subjectTable.comparatorProperty());
        subjectTable.setItems(sortedData);
    }
}
