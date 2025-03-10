package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentManagementController {

    @FXML private TableView<StudentRecord> studentTable;
    @FXML private TableColumn<StudentRecord, String> studentIdColumn;
    @FXML private TableColumn<StudentRecord, String> studentNameColumn;
    @FXML private TableColumn<StudentRecord, String> academicLevelColumn;
    @FXML private TableColumn<StudentRecord, String> emailColumn;
    @FXML private TableColumn<StudentRecord, String> currentSemesterColumn;

    @FXML private TextField studentNameField;
    @FXML private TextField academicLevelField;
    @FXML private TextField emailField;
    @FXML private TextField currentSemesterField;

    private ObservableList<StudentRecord> studentList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        academicLevelColumn.setCellValueFactory(new PropertyValueFactory<>("academicLevel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        currentSemesterColumn.setCellValueFactory(new PropertyValueFactory<>("currentSemester"));

        studentTable.setItems(studentList);

        // --- Sample Student Data from UMS ---
        studentList.addAll(
                new StudentRecord("S20250001", "Alice Smith", "123 Maple St.", "555-1234", "alice@example.edu", "Undergraduate", "Fall 2025", "default", "ENG101", "", "40%", "default123"),
                new StudentRecord("S20250002", "Bob Johnson", "456 Oak St.", "555-5678", "bob@example.edu", "Graduate", "Fall 2025", "default", "CS201", "The detection of oil under ice by remote mode conversion of ultrasound", "50%", "default123"),
                new StudentRecord("S20250003", "Carol Williams", "789 Pine St.", "555-9012", "carol@example.edu", "Graduate", "Fall 2025", "default", "ENGG402, HIST101", "On the economic optimality of marine reserves when fishing damages habitat", "50%", "default123"),
                new StudentRecord("S20250004", "Lucka Racki", "1767 Jane St.", "439-9966", "lucka@example.edu", "Undergraduate", "Fall 2025", "default", "Bio300", "", "60%", "default123"),
                new StudentRecord("S20250005", "David Lee", "90 Elm St.", "555-3456", "lee@example.edu", "Undergraduate", "Fall 2025", "default", "HIST101", "-", "50%", "default123"),
                new StudentRecord("S20250006", "Emily Brown", "111 Oak Ave.", "555-7890", "brown@example.edu", "Graduate", "Fall 2025", "default", "Chem200", "Synthesis and Characterization of Novel Catalysts", "50%", "default123"),
                new StudentRecord("S20250007", "George Smith", "222 Pine Rd.", "555-2345", "smith@example.edu", "Undergraduate", "Fall 2025", "default", "Music102", "-", "40%", "default123"),
                new StudentRecord("S20250008", "Helen Jones", "333 Maple Dr.", "555-4567", "jones@example.edu", "Graduate", "Fall 2025", "default", "PSYCHO100, Music102, HIST101", "The Effects of Stress on Cognitive Function", "50%", "default123"),
                new StudentRecord("S20250009", "Isaac Clark", "444 Cedar Ln.", "555-8901", "clark@example.edu", "Undergraduate", "Fall 2025", "default", "BIo300", "-", "50%", "default123"),
                new StudentRecord("S20250010", "Jennifer Davis", "555 Oakwood Pl", "555-3456", "davis@example.edu", "Graduate", "Fall 2025", "default", "HIST101", "The Renaissance: Art, Culture, and Society", "20%", "default123")
        );
    }

    @FXML
    private void handleAddStudent(){
        String name = studentNameField.getText().trim();
        String level = academicLevelField.getText().trim();
        String email = emailField.getText().trim();
        String semester = currentSemesterField.getText().trim();

        if(name.isEmpty() || level.isEmpty() || email.isEmpty() || semester.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Missing Data");
            alert.setContentText("Name, academic level, email, and semester are required.");
            alert.showAndWait();
            return;
        }
        // Auto–generate an ID for demonstration.
        String id = "S" + System.currentTimeMillis();
        studentList.add(new StudentRecord(id, name, "N/A", "N/A", email, level, semester, "default", "", "", "0%", "default123"));
        studentNameField.clear();
        academicLevelField.clear();
        emailField.clear();
        currentSemesterField.clear();
    }

    public static class StudentRecord {
        private final String studentId;
        private final String name;
        private final String address;
        private final String telephone;
        private final String email;
        private final String academicLevel;
        private final String currentSemester;
        private final String profilePhoto;
        private final String subjectsRegistered;
        private final String thesisTitle;
        private final String progress;
        private final String password;

        public StudentRecord(String studentId, String name, String address, String telephone, String email,
                             String academicLevel, String currentSemester, String profilePhoto,
                             String subjectsRegistered, String thesisTitle, String progress, String password) {
            this.studentId = studentId;
            this.name = name;
            this.address = address;
            this.telephone = telephone;
            this.email = email;
            this.academicLevel = academicLevel;
            this.currentSemester = currentSemester;
            this.profilePhoto = profilePhoto;
            this.subjectsRegistered = subjectsRegistered;
            this.thesisTitle = thesisTitle;
            this.progress = progress;
            this.password = password;
        }

        public String getStudentId(){ return studentId; }
        public String getName(){ return name; }
        public String getAddress(){ return address; }
        public String getTelephone(){ return telephone; }
        public String getEmail(){ return email; }
        public String getAcademicLevel(){ return academicLevel; }
        public String getCurrentSemester(){ return currentSemester; }
        public String getProfilePhoto(){ return profilePhoto; }
        public String getSubjectsRegistered(){ return subjectsRegistered; }
        public String getThesisTitle(){ return thesisTitle; }
        public String getProgress(){ return progress; }
        public String getPassword(){ return password; }
    }
}
