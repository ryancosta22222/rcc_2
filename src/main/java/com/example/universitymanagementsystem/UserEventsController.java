package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserEventsController {

    @FXML private TableView<Event> allEventsTable;
    @FXML private TableColumn<Event, String> eventCodeColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> dateTimeColumn;
    @FXML private TableColumn<Event, String> costColumn;
    @FXML private TableColumn<Event, Number> capacityColumn;

    @FXML private ListView<String> myRegisteredEventsList;
    @FXML private Label eventDetailsLabel; // show details of selected event
    @FXML private Button registerButton;

    // The student’s own list of events (by code) or a direct reference
    private ObservableList<String> myRegisteredEventCodes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Show the shared event list
        allEventsTable.setItems(EventManagementController.getEventList());

        // When user selects an event, display details
        allEventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String info = "Event: " + newVal.getEventName() + "\n" +
                        "Code: " + newVal.getEventCode() + "\n" +
                        "Location: " + newVal.getLocation() + "\n" +
                        "Date/Time: " + newVal.getDateTime() + "\n" +
                        "Description: " + newVal.getDescription() + "\n" +
                        "Capacity: " + newVal.getCapacity() + "\n" +
                        "Cost: " + newVal.getCost() + "\n" +
                        "Registered: " + newVal.getRegisteredStudents().size() + "\n" +
                        "Header Image: " + newVal.getHeaderImagePath();
                eventDetailsLabel.setText(info);
            } else {
                eventDetailsLabel.setText("");
            }
        });

        // Populate the student’s currently registered events if needed
        myRegisteredEventsList.setItems(myRegisteredEventCodes);

        // If an admin logs in by mistake, hide the register button
        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            registerButton.setDisable(true);
        }
    }

    /**
     * Called when the student clicks "Register" for the selected event.
     */
    @FXML
    private void handleRegisterForEvent() {
        User currentUser = Session.getInstance().getUser();
        if (currentUser == null || !"STUDENT".equalsIgnoreCase(currentUser.getRole())) {
            showAlert(Alert.AlertType.WARNING, "Access Denied", "Only students can register for events.");
            return;
        }

        Event selectedEvent = allEventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event to register.");
            return;
        }

        // Check capacity
        if (!selectedEvent.canRegister()) {
            showAlert(Alert.AlertType.WARNING, "Capacity Reached",
                    "Sorry, this event has reached its maximum capacity.");
            return;
        }

        // Check if already registered
        if (selectedEvent.getRegisteredStudents().contains(currentUser.getUsername())) {
            showAlert(Alert.AlertType.INFORMATION, "Already Registered",
                    "You have already registered for this event.");
            return;
        }

        // Register
        selectedEvent.getRegisteredStudents().add(currentUser.getUsername());
        myRegisteredEventCodes.add(selectedEvent.getEventCode());

        // (Optional) “Send” a confirmation email
        sendConfirmationEmail(currentUser.getUsername(), selectedEvent);

        showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                "You have been registered for: " + selectedEvent.getEventName());
    }

    /**
     * Simple placeholder method for sending a confirmation email.
     */
    private void sendConfirmationEmail(String studentUsername, Event event) {
        // In a real app, integrate an email API or service
        System.out.println("Sending confirmation email to " + studentUsername + " for event " + event.getEventCode());
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
