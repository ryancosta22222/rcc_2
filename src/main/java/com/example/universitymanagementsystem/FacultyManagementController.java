package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FacultyManagementController {

    @FXML private TableView<Faculty> facultyTable;
    @FXML private TableColumn<Faculty, String> idColumn;
    @FXML private TableColumn<Faculty, String> nameColumn;
    @FXML private TableColumn<Faculty, String> degreeColumn;
    @FXML private TableColumn<Faculty, String> researchColumn;
    @FXML private TableColumn<Faculty, String> emailColumn;
    @FXML private TableColumn<Faculty, String> officeColumn;
    @FXML private TableColumn<Faculty, String> coursesColumn; // Courses Offered

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField degreeField;
    @FXML private TextField researchField;
    @FXML private TextField emailField;
    @FXML private TextField officeField;
    @FXML private TextField coursesField;

    // Shared list of faculty profiles
    private static ObservableList<Faculty> facultyList = FXCollections.observableArrayList();

    // Currently selected faculty member (for editing/deleting)
    private Faculty selectedFaculty = null;

    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
        researchColumn.setCellValueFactory(new PropertyValueFactory<>("researchInterest"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        officeColumn.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));
        coursesColumn.setCellValueFactory(new PropertyValueFactory<>("coursesOffered"));

        // Preload sample data if empty
        if (facultyList.isEmpty()){
            facultyList.addAll(
                    new Faculty("F0001", "Dr. Alan Turing", "Ph.D.", "Computational Theory", "turing@university.edu", "Room 201", "Calculus I"),
                    new Faculty("F0002", "Prof. Emily Brontë", "Master's", "English Literature", "bronte@university.edu", "Room 202", "Literature Basics"),
                    new Faculty("F0003", "Dr. Grace Hopper", "Ph.D.", "Computer Programming", "hopper@university.edu", "Lab 203", "Intro to Programming")
            );
        }
        facultyTable.setItems(facultyList);

        // When a faculty is selected, populate input fields
        facultyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedFaculty = newVal;
            if(newVal != null){
                idField.setText(newVal.getId());
                nameField.setText(newVal.getName());
                degreeField.setText(newVal.getDegree());
                researchField.setText(newVal.getResearchInterest());
                emailField.setText(newVal.getEmail());
                officeField.setText(newVal.getOfficeLocation());
                coursesField.setText(newVal.getCoursesOffered());
            }
        });
    }

    @FXML
    private void handleAddFaculty(){
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String degree = degreeField.getText().trim();
        String research = researchField.getText().trim();
        String email = emailField.getText().trim();
        String office = officeField.getText().trim();
        String courses = coursesField.getText().trim();

        if(id.isEmpty() || name.isEmpty() || email.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Validation Error", "ID, Name, and Email are required.");
            return;
        }

        // Check unique email (username)
        for(Faculty f: facultyList){
            if(f.getEmail().equalsIgnoreCase(email)){
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Email must be unique.");
                return;
            }
        }

        // Default password is assumed to be "default123" (handled elsewhere in user management)
        Faculty newFaculty = new Faculty(id, name, degree, research, email, office, courses);
        facultyList.add(newFaculty);
        clearFields();
    }

    @FXML
    private void handleEditFaculty(){
        if(selectedFaculty == null){
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a faculty member to edit.");
            return;
        }
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String degree = degreeField.getText().trim();
        String research = researchField.getText().trim();
        String email = emailField.getText().trim();
        String office = officeField.getText().trim();
        String courses = coursesField.getText().trim();

        if(id.isEmpty() || name.isEmpty() || email.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Validation Error", "ID, Name, and Email are required.");
            return;
        }
        // If email changed, ensure uniqueness
        if(!selectedFaculty.getEmail().equalsIgnoreCase(email)){
            for(Faculty f: facultyList){
                if(f.getEmail().equalsIgnoreCase(email)){
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Email must be unique.");
                    return;
                }
            }
        }
        // Update faculty data
        selectedFaculty.setId(id);
        selectedFaculty.setName(name);
        selectedFaculty.setDegree(degree);
        selectedFaculty.setResearchInterest(research);
        selectedFaculty.setEmail(email);
        selectedFaculty.setOfficeLocation(office);
        selectedFaculty.setCoursesOffered(courses);

        facultyTable.refresh();
        clearFields();
    }

    @FXML
    private void handleDeleteFaculty(){
        if(selectedFaculty == null){
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a faculty member to delete.");
            return;
        }
        facultyList.remove(selectedFaculty);
        clearFields();
    }

    private void clearFields(){
        idField.clear();
        nameField.clear();
        degreeField.clear();
        researchField.clear();
        emailField.clear();
        officeField.clear();
        coursesField.clear();
        facultyTable.getSelectionModel().clearSelection();
        selectedFaculty = null;
    }

    private void showAlert(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Public getter for sharing with student view if needed
    public static ObservableList<Faculty> getFacultyList(){
        return facultyList;
    }

    // Inner class representing a faculty member.
    public static class Faculty {
        private String id;
        private String name;
        private String degree;
        private String researchInterest;
        private String email;
        private String officeLocation;
        private String coursesOffered;

        public Faculty(String id, String name, String degree, String researchInterest, String email, String officeLocation, String coursesOffered){
            this.id = id;
            this.name = name;
            this.degree = degree;
            this.researchInterest = researchInterest;
            this.email = email;
            this.officeLocation = officeLocation;
            this.coursesOffered = coursesOffered;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDegree() { return degree; }
        public void setDegree(String degree) { this.degree = degree; }

        public String getResearchInterest() { return researchInterest; }
        public void setResearchInterest(String researchInterest) { this.researchInterest = researchInterest; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getOfficeLocation() { return officeLocation; }
        public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

        public String getCoursesOffered() { return coursesOffered; }
        public void setCoursesOffered(String coursesOffered) { this.coursesOffered = coursesOffered; }
    }
}
