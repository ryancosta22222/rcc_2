package com.example.universitymanagementsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class CourseManagementAdminController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> courseCodeColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> subjectCodeColumn;
    @FXML private TableColumn<Course, String> sectionColumn;
    @FXML private TableColumn<Course, String> teacherNameColumn;
    @FXML private TableColumn<Course, Integer> capacityColumn;
    @FXML private TableColumn<Course, String> lectureTimeColumn;
    @FXML private TableColumn<Course, String> finalExamColumn;
    @FXML private TableColumn<Course, String> locationColumn;

    @FXML private TextField courseCodeField;
    @FXML private TextField courseNameField;
    @FXML private TextField subjectCodeField;
    @FXML private TextField sectionField;
    @FXML private TextField teacherField;
    @FXML private TextField capacityField;
    @FXML private TextField lectureTimeField;
    @FXML private TextField finalExamField;
    @FXML private TextField locationField;

    // Instead of a local list, use the shared CourseDataStore.
    private ObservableList<Course> courseList = CourseDataStore.getCourses();
    private Course selectedCourse = null;

    @FXML
    private void initialize() {
        // Set up table columns.
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lectureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        courseTable.setItems(courseList);

        // Populate fields when a course is selected.
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCourse = newSelection;
            if (newSelection != null) {
                courseCodeField.setText(newSelection.getCourseCode());
                courseNameField.setText(newSelection.getCourseName());
                subjectCodeField.setText(newSelection.getSubjectCode());
                sectionField.setText(newSelection.getSection());
                teacherField.setText(newSelection.getTeacherName());
                capacityField.setText(String.valueOf(newSelection.getCapacity()));
                lectureTimeField.setText(newSelection.getLectureTime());
                finalExamField.setText(newSelection.getFinalExamDateTime());
                locationField.setText(newSelection.getLocation());
            }
        });
    }

    @FXML
    private void handleAddCourse() {
        try {
            String code = courseCodeField.getText().trim();
            String name = courseNameField.getText().trim();
            String subj = subjectCodeField.getText().trim();
            String section = sectionField.getText().trim();
            String teacher = teacherField.getText().trim();
            int cap = Integer.parseInt(capacityField.getText().trim());
            String lecture = lectureTimeField.getText().trim();
            String exam = finalExamField.getText().trim();
            String loc = locationField.getText().trim();

            if (code.isEmpty() || name.isEmpty() || subj.isEmpty() || section.isEmpty() ||
                    teacher.isEmpty() || lecture.isEmpty() || exam.isEmpty() || loc.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Conflict detection: check that the teacher isn’t scheduled at the same lecture time.
            for (Course c : courseList) {
                if (c.getTeacherName().equalsIgnoreCase(teacher) &&
                        c.getLectureTime().equalsIgnoreCase(lecture)) {
                    Alert conflictAlert = new Alert(Alert.AlertType.WARNING);
                    conflictAlert.setTitle("Scheduling Conflict");
                    conflictAlert.setHeaderText(null);
                    conflictAlert.setContentText("Teacher " + teacher + " is already assigned to a course at " + lecture + ".");
                    conflictAlert.showAndWait();
                    return;
                }
            }

            Course newCourse = new Course(code, name, subj, section, cap, lecture, exam, loc, teacher);
            courseList.add(newCourse);
            updateStudentRecords();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Capacity must be a number.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", e.getMessage());
        }
    }

    @FXML
    private void handleEditCourse() {
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to edit.");
            return;
        }
        try {
            String code = courseCodeField.getText().trim();
            String name = courseNameField.getText().trim();
            String subj = subjectCodeField.getText().trim();
            String section = sectionField.getText().trim();
            String teacher = teacherField.getText().trim();
            int cap = Integer.parseInt(capacityField.getText().trim());
            String lecture = lectureTimeField.getText().trim();
            String exam = finalExamField.getText().trim();
            String loc = locationField.getText().trim();

            if (code.isEmpty() || name.isEmpty() || subj.isEmpty() || section.isEmpty() ||
                    teacher.isEmpty() || lecture.isEmpty() || exam.isEmpty() || loc.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Conflict detection: ensure no other course conflicts with the new scheduling.
            for (Course c : courseList) {
                if (c != selectedCourse &&
                        c.getTeacherName().equalsIgnoreCase(teacher) &&
                        c.getLectureTime().equalsIgnoreCase(lecture)) {
                    showAlert(Alert.AlertType.WARNING, "Scheduling Conflict",
                            "Teacher " + teacher + " is already assigned to another course at " + lecture + ".");
                    return;
                }
            }

            // Preserve current enrollments.
            ObservableList<String> currentEnrollments = selectedCourse.getEnrolledStudents();
            Course updatedCourse = new Course(code, name, subj, section, cap, lecture, exam, loc, teacher);
            updatedCourse.getEnrolledStudents().addAll(currentEnrollments);

            int index = courseList.indexOf(selectedCourse);
            courseList.set(index, updatedCourse);
            selectedCourse = updatedCourse;
            updateStudentRecords();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Capacity must be a number.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteCourse() {
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to delete.");
            return;
        }
        courseList.remove(selectedCourse);
        updateStudentRecords();
        clearFields();
        selectedCourse = null;
    }

    @FXML
    private void handleAssignFaculty() {
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to assign faculty.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog(selectedCourse.getTeacherName());
        dialog.setTitle("Assign Faculty");
        dialog.setHeaderText("Assign a teacher to the course");
        dialog.setContentText("Enter Teacher Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String newTeacher = result.get().trim();
            if (newTeacher.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Teacher name cannot be empty.");
                return;
            }
            // Conflict detection for teacher scheduling.
            for (Course c : courseList) {
                if (c != selectedCourse &&
                        c.getTeacherName().equalsIgnoreCase(newTeacher) &&
                        c.getLectureTime().equalsIgnoreCase(selectedCourse.getLectureTime())) {
                    showAlert(Alert.AlertType.WARNING, "Scheduling Conflict",
                            "Teacher " + newTeacher + " is already assigned to another course at " + selectedCourse.getLectureTime() + ".");
                    return;
                }
            }
            selectedCourse.setTeacherName(newTeacher);
            courseTable.refresh();
            updateStudentRecords();
        }
    }

    @FXML
    private void handleManageEnrollments() {
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to manage enrollments.");
            return;
        }

        // Show current enrollments.
        String enrolledList = selectedCourse.getEnrolledStudents().isEmpty() ?
                "None" : String.join(", ", selectedCourse.getEnrolledStudents());
        showAlert(Alert.AlertType.INFORMATION, "Enrolled Students",
                "Current enrollments for " + selectedCourse.getCourseName() + ":\n" + enrolledList);

        // Option to add an enrollment.
        TextInputDialog addDialog = new TextInputDialog();
        addDialog.setTitle("Add Enrollment");
        addDialog.setHeaderText("Add Student to Course");
        addDialog.setContentText("Enter Student ID:");
        Optional<String> addResult = addDialog.showAndWait();
        if (addResult.isPresent() && !addResult.get().trim().isEmpty()) {
            String studentId = addResult.get().trim();
            if (selectedCourse.getEnrolledStudents().size() >= selectedCourse.getCapacity()) {
                showAlert(Alert.AlertType.WARNING, "Enrollment Error", "Course capacity reached. Cannot add more students.");
            } else if (selectedCourse.getEnrolledStudents().contains(studentId)) {
                showAlert(Alert.AlertType.INFORMATION, "Enrollment Info", "Student already enrolled.");
            } else {
                selectedCourse.enrollStudent(studentId);
                updateStudentRecords();
            }
        }

        // Option to remove an enrollment.
        TextInputDialog removeDialog = new TextInputDialog();
        removeDialog.setTitle("Remove Enrollment");
        removeDialog.setHeaderText("Remove Student from Course");
        removeDialog.setContentText("Enter Student ID:");
        Optional<String> removeResult = removeDialog.showAndWait();
        if (removeResult.isPresent() && !removeResult.get().trim().isEmpty()) {
            String studentId = removeResult.get().trim();
            if (!selectedCourse.getEnrolledStudents().contains(studentId)) {
                showAlert(Alert.AlertType.INFORMATION, "Enrollment Info", "Student is not enrolled in this course.");
            } else {
                selectedCourse.removeStudent(studentId);
                updateStudentRecords();
            }
        }
    }

    private void clearFields() {
        courseCodeField.clear();
        courseNameField.clear();
        subjectCodeField.clear();
        sectionField.clear();
        teacherField.clear();
        capacityField.clear();
        lectureTimeField.clear();
        finalExamField.clear();
        locationField.clear();
        courseTable.getSelectionModel().clearSelection();
    }

    // Placeholder method simulating updating student records when courses change.
    private void updateStudentRecords() {
        System.out.println("Student records updated to reflect course changes.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
