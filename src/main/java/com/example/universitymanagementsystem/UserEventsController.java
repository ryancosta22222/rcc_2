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
    @FXML private Label eventDetailsLabel;
    @FXML private Button registerButton;
    @FXML private ImageView eventHeaderImageView;

    private ObservableList<String> myRegisteredEventCodes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        allEventsTable.setItems(EventManagementController.getEventList());

        loadMyRegisteredEvents();

        allEventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateEventDetails(newVal));

        myRegisteredEventsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            Event selected = findEventByCode(newVal);
            updateEventDetails(selected);
        });

        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            registerButton.setDisable(true);
        }
    }

    private void loadMyRegisteredEvents() {
        myRegisteredEventCodes.clear();
        User currentUser = Session.getInstance().getUser();

        if (currentUser == null) return;

        for (Event event : EventManagementController.getEventList()) {
            if (event.getRegisteredStudents().contains(currentUser.getUsername())) {
                myRegisteredEventCodes.add(event.getEventCode());
            }
        }

        myRegisteredEventsList.setItems(myRegisteredEventCodes);
    }

    private Event findEventByCode(String eventCode) {
        for (Event event : EventManagementController.getEventList()) {
            if (event.getEventCode().equalsIgnoreCase(eventCode)) {
                return event;
            }
        }
        return null;
    }

    private void updateEventDetails(Event event) {
        if (event != null) {
            eventDetailsLabel.setText(
                    "Event: " + event.getEventName() + "\n" +
                            "Code: " + event.getEventCode() + "\n" +
                            "Location: " + event.getLocation() + "\n" +
                            "Date/Time: " + event.getDateTime() + "\n" +
                            "Description: " + event.getDescription() + "\n" +
                            "Capacity: " + event.getCapacity() + "\n" +
                            "Cost: " + event.getCost() + "\n" +
                            "Registered: " + event.getRegisteredStudents().size()
            );

            if (event.getHeaderImagePath().startsWith("default")) {
                String imagePath = getClass().getResource("/images/" + event.getHeaderImagePath()).toExternalForm();
                eventHeaderImageView.setImage(new Image(imagePath, 200, 150, true, true, false));
            } else {
                eventHeaderImageView.setImage(new Image(event.getHeaderImagePath(), 200, 150, true, true, false));
            }
        } else {
            eventDetailsLabel.setText("Select an event to view details.");
            eventHeaderImageView.setImage(null);
        }
    }

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
            showAlert(Alert.AlertType.WARNING, "Capacity Reached", "Sorry, this event has reached its maximum capacity.");
            return;
        }

        if (selectedEvent.getRegisteredStudents().contains(currentUser.getUsername())) {
            showAlert(Alert.AlertType.INFORMATION, "Already Registered", "You have already registered for this event.");
            return;
        }

        selectedEvent.getRegisteredStudents().add(currentUser.getUsername());
        myRegisteredEventCodes.add(selectedEvent.getEventCode());
        sendConfirmationEmail(currentUser.getUsername(), selectedEvent);

        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have been registered for: " + selectedEvent.getEventName());
    }

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

