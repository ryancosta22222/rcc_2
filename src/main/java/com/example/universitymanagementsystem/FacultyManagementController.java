package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FacultyManagementController {

    @FXML private TableView<Faculty> facultyTable;
    @FXML private TableColumn<Faculty, String> idColumn;
    @FXML private TableColumn<Faculty, String> nameColumn;
    @FXML private TableColumn<Faculty, String> degreeColumn;
    @FXML private TableColumn<Faculty, String> researchColumn;
    @FXML private TableColumn<Faculty, String> emailColumn;
    @FXML private TableColumn<Faculty, String> officeColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField degreeField;
    @FXML private TextField researchField;
    @FXML private TextField emailField;
    @FXML private TextField officeField;

    private ObservableList<Faculty> facultyList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind table columns to Faculty properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
        researchColumn.setCellValueFactory(new PropertyValueFactory<>("researchInterest"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        officeColumn.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));
        facultyTable.setItems(facultyList);

        // Preload sample faculty data from your UMS data
        facultyList.addAll(
                new Faculty("F0001", "Dr. Alan Turing", "Ph.D.", "Computational Theory", "turing@university.edu", "Room 201", "Calculus I"),
                new Faculty("F0002", "Prof. Emily Brontë", "Master's", "English Literature", "bronte@university.edu", "Room 202", "Literature Basics"),
                new Faculty("F0003", "Dr. Grace Hopper", "Ph.D.", "Computer Programming", "hopper@university.edu", "Lab 203", "Intro to Programming"),
                new Faculty("F0004", "Dr. Lakyn Copeland", "Master's", "English Literature", "copeland@university.edu", "Room 201", "Introduction to French"),
                new Faculty("F0005", "Albozr Gharabaghi", "Ph.D.", "Water and Soil", "gharabaghi@university.edu", "Lab 202", "Water Resources")
        );
    }

    @FXML
    private void handleAddFaculty() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String degree = degreeField.getText().trim();
        String research = researchField.getText().trim();
        String email = emailField.getText().trim();
        String office = officeField.getText().trim();

        // Basic validation: ensure required fields are not empty
        if (!id.isEmpty() && !name.isEmpty() && !email.isEmpty()) {
            Faculty newFaculty = new Faculty(id, name, degree, research, email, office, "");
            facultyList.add(newFaculty);
            idField.clear();
            nameField.clear();
            degreeField.clear();
            researchField.clear();
            emailField.clear();
            officeField.clear();
        }
    }

    // Future methods: handleEditFaculty() and handleDeleteFaculty() can be added similarly.

    // Faculty class representing the data structure for a faculty member.
    public static class Faculty {
        private final String id;
        private final String name;
        private final String degree;
        private final String researchInterest;
        private final String email;
        private final String officeLocation;
        private final String coursesOffered; // This can be updated to a List if needed

        public Faculty(String id, String name, String degree, String researchInterest, String email, String officeLocation, String coursesOffered) {
            this.id = id;
            this.name = name;
            this.degree = degree;
            this.researchInterest = researchInterest;
            this.email = email;
            this.officeLocation = officeLocation;
            this.coursesOffered = coursesOffered;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getDegree() { return degree; }
        public String getResearchInterest() { return researchInterest; }
        public String getEmail() { return email; }
        public String getOfficeLocation() { return officeLocation; }
        public String getCoursesOffered() { return coursesOffered; }
    }
}
