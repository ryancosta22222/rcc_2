package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserEventsController {

    @FXML private TableView<Event> allEventsTable;
    @FXML private TableColumn<Event, String> eventCodeColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> dateTimeColumn;
    @FXML private TableColumn<Event, String> costColumn;
    @FXML private TableColumn<Event, Number> capacityColumn;

    @FXML private ListView<String> myRegisteredEventsList;
    @FXML private Label eventDetailsLabel; // shows details of the selected event
    @FXML private Button registerButton;
    @FXML private ImageView eventHeaderImageView; // new ImageView to show header image

    // The student’s own list of registered event codes.
    private ObservableList<String> myRegisteredEventCodes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Load the shared event list.
        allEventsTable.setItems(EventManagementController.getEventList());

        // When a student selects an event, update the details, including the header image.
        allEventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String info = "Event: " + newVal.getEventName() + "\n" +
                        "Code: " + newVal.getEventCode() + "\n" +
                        "Location: " + newVal.getLocation() + "\n" +
                        "Date/Time: " + newVal.getDateTime() + "\n" +
                        "Description: " + newVal.getDescription() + "\n" +
                        "Capacity: " + newVal.getCapacity() + "\n" +
                        "Cost: " + newVal.getCost() + "\n" +
                        "Registered: " + newVal.getRegisteredStudents().size();
                eventDetailsLabel.setText(info);

                // Load the header image.
                if (newVal.getHeaderImagePath().startsWith("default")) {
                    // Load default image from resources under /images/
                    String imagePath = getClass().getResource("/images/" + newVal.getHeaderImagePath()).toExternalForm();
                    eventHeaderImageView.setImage(new Image(imagePath));
                } else {
                    // Load the image from the file system (or URL).
                    eventHeaderImageView.setImage(new Image(newVal.getHeaderImagePath()));
                }
            } else {
                eventDetailsLabel.setText("");
                eventHeaderImageView.setImage(null);
            }
        });

        myRegisteredEventsList.setItems(myRegisteredEventCodes);

        // Disable the register button if an admin is logged in.
        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            registerButton.setDisable(true);
        }
    }

    /**
     * Called when a student clicks "Register" for the selected event.
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

        if (!selectedEvent.canRegister()) {
            showAlert(Alert.AlertType.WARNING, "Capacity Reached",
                    "Sorry, this event has reached its maximum capacity.");
            return;
        }

        if (selectedEvent.getRegisteredStudents().contains(currentUser.getUsername())) {
            showAlert(Alert.AlertType.INFORMATION, "Already Registered",
                    "You have already registered for this event.");
            return;
        }

        selectedEvent.getRegisteredStudents().add(currentUser.getUsername());
        myRegisteredEventCodes.add(selectedEvent.getEventCode());
        sendConfirmationEmail(currentUser.getUsername(), selectedEvent);

        showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                "You have been registered for: " + selectedEvent.getEventName());
    }

    /**
     * Placeholder for sending a confirmation email.
     */
    private void sendConfirmationEmail(String studentUsername, Event event) {
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
