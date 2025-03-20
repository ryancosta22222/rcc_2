package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private ImageView headerImageView;  // For displaying the image

    @FXML private ListView<String> registeredStudentsList;

    // Shared event list
    private static ObservableList<Event> eventList = FXCollections.observableArrayList();

    // Currently selected event
    private Event selectedEvent = null;

    @FXML
    private void initialize() {
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Initialize with sample data if empty
        if (eventList.isEmpty()) {
            eventList.addAll(
                    new Event("EV001", "Welcome Seminar", "Orientation week", "default_header.png",
                            "Auditorium", "9/1/2025 10:00", 100, "Free"),
                    new Event("EV002", "Research Workshop", "Graduate workshop", "default_header.png",
                            "Lab 301", "10/5/2025 14:00", 50, "Paid ($20)")
            );
        }

        eventTable.setItems(eventList);

        // When an event is selected, populate the fields and load the image
        eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedEvent = newVal;
            if (newVal != null) {
                eventCodeField.setText(newVal.getEventCode());
                eventNameField.setText(newVal.getEventName());
                descriptionField.setText(newVal.getDescription());
                headerImageLabel.setText(newVal.getHeaderImagePath());
                // Load image into the ImageView.
                // For default images, assume they're in the resources under /images/
                if (newVal.getHeaderImagePath().startsWith("default")) {
                    String imagePath = getClass().getResource("/images/" + newVal.getHeaderImagePath()).toExternalForm();
                    headerImageView.setImage(new Image(imagePath));
                } else {
                    headerImageView.setImage(new Image(newVal.getHeaderImagePath()));
                }
                locationField.setText(newVal.getLocation());
                dateTimeField.setText(newVal.getDateTime());
                costField.setText(newVal.getCost());
                capacityField.setText(String.valueOf(newVal.getCapacity()));
                registeredStudentsList.setItems(newVal.getRegisteredStudents());
            } else {
                clearFields();
            }
        });

        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && "STUDENT".equalsIgnoreCase(currentUser.getRole())) {
            disableAdminControls();
        }
    }

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

            if (code.isEmpty() || name.isEmpty() || loc.isEmpty() || dt.isEmpty()) {
                throw new IllegalArgumentException("Code, Name, Location, and DateTime are required.");
            }

            for (Event e : eventList) {
                if (e.getEventCode().equalsIgnoreCase(code)) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Event code must be unique.");
                    return;
                }
            }

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

            if (code.isEmpty() || name.isEmpty() || loc.isEmpty() || dt.isEmpty()) {
                throw new IllegalArgumentException("Code, Name, Location, and DateTime are required.");
            }

            if (!selectedEvent.getEventCode().equalsIgnoreCase(code)) {
                for (Event e : eventList) {
                    if (e.getEventCode().equalsIgnoreCase(code)) {
                        showAlert(Alert.AlertType.WARNING, "Validation Error", "Event code must be unique.");
                        return;
                    }
                }
            }

            selectedEvent.setEventCode(code);
            selectedEvent.setEventName(name);
            selectedEvent.setDescription(desc);
            selectedEvent.setHeaderImagePath(headerImg.isEmpty() ? "default_header.png" : headerImg);
            selectedEvent.setLocation(loc);
            selectedEvent.setDateTime(dt);
            selectedEvent.setCost(cost);
            selectedEvent.setCapacity(cap);

            eventTable.refresh();
            clearFields();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Capacity must be a number.");
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", ex.getMessage());
        }
    }

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

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Header Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) eventTable.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            headerImageLabel.setText(selectedFile.getAbsolutePath());
            headerImageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

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

        showAlert(Alert.AlertType.INFORMATION, "Attendee List", sb.toString());
    }

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

    private void clearFields() {
        eventCodeField.clear();
        eventNameField.clear();
        descriptionField.clear();
        headerImageLabel.setText("");
        headerImageView.setImage(null);
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
        headerImageView.setDisable(true);
        locationField.setDisable(true);
        dateTimeField.setDisable(true);
        costField.setDisable(true);
        capacityField.setDisable(true);
    }

    public static ObservableList<Event> getEventList() {
        return eventList;
    }
}
