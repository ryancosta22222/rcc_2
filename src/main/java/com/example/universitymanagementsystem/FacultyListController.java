package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FacultyListController {

    @FXML private TableView<FacultySummary> facultyTable;
    @FXML private TableColumn<FacultySummary, String> nameColumn;
    @FXML private TableColumn<FacultySummary, String> emailColumn;
    @FXML private TableColumn<FacultySummary, String> officeColumn;
    @FXML private TableColumn<FacultySummary, String> researchColumn;

    private ObservableList<FacultySummary> summaryList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        officeColumn.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));
        researchColumn.setCellValueFactory(new PropertyValueFactory<>("researchInterest"));

        // Populate summary list from the shared faculty list with limited info.
        for(FacultyManagementController.Faculty f : FacultyManagementController.getFacultyList()){
            summaryList.add(new FacultySummary(f.getName(), f.getEmail(), f.getOfficeLocation(), f.getResearchInterest()));
        }
        facultyTable.setItems(summaryList);
    }

    // Inner class representing a limited faculty summary.
    public static class FacultySummary {
        private final String name;
        private final String email;
        private final String officeLocation;
        private final String researchInterest;

        public FacultySummary(String name, String email, String officeLocation, String researchInterest){
            this.name = name;
            this.email = email;
            this.officeLocation = officeLocation;
            this.researchInterest = researchInterest;
        }

        public String getName(){ return name; }
        public String getEmail(){ return email; }
        public String getOfficeLocation(){ return officeLocation; }
        public String getResearchInterest(){ return researchInterest; }
    }
}
