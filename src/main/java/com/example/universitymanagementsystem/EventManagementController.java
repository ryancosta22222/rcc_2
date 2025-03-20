package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EventManagementController {

    @FXML private TableView<Event> eventTable;
    @FXML private TableColumn<Event, String> eventCodeColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> locationColumn;
    @FXML private TableColumn<Event, String> dateTimeColumn;
    @FXML private TableColumn<Event, String> costColumn;
    @FXML private TableColumn<Event, Number> capacityColumn;

    @FXML private TextField eventCodeField;
    @FXML private TextField eventNameField;
    @FXML private TextField descriptionField;
    @FXML private TextField locationField;
    @FXML private TextField dateTimeField;
    @FXML private TextField costField;
    @FXML private TextField capacityField;
    @FXML private Label headerImageLabel;

    // For showing the list of registered students
    @FXML private ListView<String> registeredStudentsList;

    // The static list of events that admin & students share
    private static ObservableList<Event> eventList = FXCollections.observableArrayList();

    // Track the currently selected event
    private Event selectedEvent = null;

    @FXML
    private void initialize() {
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Sample data if empty
        if (eventList.isEmpty()) {
            eventList.addAll(
                    new Event("EV001", "Welcome Seminar", "Orientation week", "default_header.png",
                            "Auditorium", "9/1/2025 10:00", 100, "Free"),
                    new Event("EV002", "Research Workshop", "Graduate workshop", "default_header.png",
                            "Lab 301", "10/5/2025 14:00", 50, "Paid ($20)")
            );
        }

        eventTable.setItems(eventList);

        // Update form fields when an event is selected
        eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedEvent = newVal;
            if (newVal != null) {
                eventCodeField.setText(newVal.getEventCode());
                eventNameField.setText(newVal.getEventName());
                descriptionField.setText(newVal.getDescription());
                headerImageLabel.setText(newVal.getHeaderImagePath());
                locationField.setText(newVal.getLocation());
                dateTimeField.setText(newVal.getDateTime());
                costField.setText(newVal.getCost());
                capacityField.setText(String.valueOf(newVal.getCapacity()));

                // Show the list of registered students
                registeredStudentsList.setItems(newVal.getRegisteredStudents());
            } else {
                clearFields();
            }
        });

        // If a Student is logged in, disable all editing controls
        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && "STUDENT".equalsIgnoreCase(currentUser.getRole())) {
            disableAdminControls();
        }
    }

    /**
     * Add a new event
     */
    @FXML
    private void handleAddEvent() {
        try {
            String code = eventCodeField.getText().trim();
            String name = eventNameField.getText().trim();
            String desc = descriptionField.getText().trim();
            String headerImg = headerImageLabel.getText().trim();
            String loc = locationField.getText().trim();
            String dt = dateTimeField.getText().trim();
            String cost = costField.getText().trim();
            int cap = Integer.parseInt(capacityField.getText().trim());

            // Basic validation
            if (code.isEmpty() || name.isEmpty() || loc.isEmpty() || dt.isEmpty()) {
                throw new IllegalArgumentException("Code, Name, Location, and DateTime are required.");
            }

            // Check for unique code
            for (Event e : eventList) {
                if (e.getEventCode().equalsIgnoreCase(code)) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Event code must be unique.");
                    return;
                }
            }

            // If no header image chosen, use a default
            if (headerImg.isEmpty()) {
                headerImg = "default_header.png";
            }

            Event newEvent = new Event(code, name, desc, headerImg, loc, dt, cap, cost);
            eventList.add(newEvent);
            clearFields();

        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Capacity must be a number.");
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", ex.getMessage());
        }
    }

    /**
     * Edit the currently selected event
     */
    @FXML
    private void handleEditEvent() {
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event to edit.");
            return;
        }

        try {
            String code = eventCodeField.getText().trim();
            String name = eventNameField.getText().trim();
            String desc = descriptionField.getText().trim();
            String headerImg = headerImageLabel.getText().trim();
            String loc = locationField.getText().trim();
            String dt = dateTimeField.getText().trim();
            String cost = costField.getText().trim();
            int cap = Integer.parseInt(capacityField.getText().trim());

            // Basic validation
            if (code.isEmpty() || name.isEmpty() || loc.isEmpty() || dt.isEmpty()) {
                throw new IllegalArgumentException("Code, Name, Location, and DateTime are required.");
            }

            // If the event code has changed, check uniqueness
            if (!selectedEvent.getEventCode().equalsIgnoreCase(code)) {
                for (Event e : eventList) {
                    if (e.getEventCode().equalsIgnoreCase(code)) {
                        showAlert(Alert.AlertType.WARNING, "Validation Error", "Event code must be unique.");
                        return;
                    }
                }
            }

            // Update the selected event
            selectedEvent.setEventCode(code);
            selectedEvent.setEventName(name);
            selectedEvent.setDescription(desc);
            selectedEvent.setHeaderImagePath(headerImg.isEmpty() ? "default_header.png" : headerImg);
            selectedEvent.setLocation(loc);
            selectedEvent.setDateTime(dt);
            selectedEvent.setCost(cost);
            selectedEvent.setCapacity(cap);

            // Force UI refresh
            eventTable.refresh();
            clearFields();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Capacity must be a number.");
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", ex.getMessage());
        }
    }

    /**
     * Delete the currently selected event
     */
    @FXML
    private void handleDeleteEvent() {
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event to delete.");
            return;
        }
        eventList.remove(selectedEvent);
        selectedEvent = null;
        clearFields();
    }

    /**
     * Opens a FileChooser to let the admin pick a new header image.
     */
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Header Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) eventTable.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            headerImageLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Generates an attendee list for the selected event (placeholder).
     * You could export to CSV, print a PDF, etc.
     */
    @FXML
    private void handleGenerateAttendeeList() {
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event first.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Attendee List for Event: ").append(selectedEvent.getEventName()).append("\n\n");
        for (String student : selectedEvent.getRegisteredStudents()) {
            sb.append(student).append("\n");
        }

        // In a real app, you'd write this to a file or send it somewhere
        showAlert(Alert.AlertType.INFORMATION, "Attendee List", sb.toString());
    }

    /**
     * Optionally remove a student from the event’s registration.
     */
    @FXML
    private void handleRemoveSelectedRegistration() {
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "No Event", "Select an event first.");
            return;
        }
        String selectedStudent = registeredStudentsList.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            selectedEvent.getRegisteredStudents().remove(selectedStudent);
        }
    }

    /**
     * Clears all input fields and deselects the event in the table.
     */
    private void clearFields() {
        eventCodeField.clear();
        eventNameField.clear();
        descriptionField.clear();
        headerImageLabel.setText("");
        locationField.clear();
        dateTimeField.clear();
        costField.clear();
        capacityField.clear();
        registeredStudentsList.setItems(null);
        eventTable.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void disableAdminControls() {
        eventCodeField.setDisable(true);
        eventNameField.setDisable(true);
        descriptionField.setDisable(true);
        headerImageLabel.setDisable(true);
        locationField.setDisable(true);
        dateTimeField.setDisable(true);
        costField.setDisable(true);
        capacityField.setDisable(true);

        // Buttons
        // (Disable them if you truly don't want a student to do any changes.)
        // handleAddEvent, handleEditEvent, handleDeleteEvent, etc.
    }

    /**
     * Provides the shared event list so user controllers can access the same data.
     */
    public static ObservableList<Event> getEventList() {
        return eventList;
    }
}
