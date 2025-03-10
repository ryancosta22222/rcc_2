package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the User Events page.
 * This class manages the display of event data in a table for students to view available events.
 */
public class UserEventsController {

    // Reference to the TableView in the FXML file where event data is displayed
    @FXML private TableView<Event> eventTable;

    // TableColumn references for displaying different attributes of an event
    @FXML private TableColumn<Event, String> eventCodeColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> descriptionColumn;
    @FXML private TableColumn<Event, String> locationColumn;
    @FXML private TableColumn<Event, String> dateTimeColumn;
    @FXML private TableColumn<Event, String> capacityColumn;
    @FXML private TableColumn<Event, String> costColumn;

    // ObservableList to hold event data that will be displayed in the TableView
    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    /**
     * This method is automatically called when the FXML file is loaded.
     * It initializes the event table by linking the table columns to the event properties
     * and preloading some sample event data.
     */
    @FXML
    private void initialize() {
        // Bind table columns to the properties of the Event class
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        // Preload sample event data to display when the page loads
        eventList.addAll(
                new Event("EV001", "Welcome Seminar", "Orientation week", "Auditorium", "9/1/2025 10:00", "100", "Free"),
                new Event("EV002", "Research Workshop", "Graduate workshop", "Lab 301", "10/5/2025 14:00", "50", "Paid ($20)")
        );

        // Set the event list to the TableView to display data
        eventTable.setItems(eventList);
    }

    /**
     * Inner class representing an Event.
     * This class holds details about an event such as event code, name, description, location, date/time, capacity, and cost.
     * The TableView in the FXML file will use this class to display event details.
     */
    public static class Event {
        private final String eventCode;
        private final String eventName;
        private final String description;
        private final String location;
        private final String dateTime;
        private final String capacity;
        private final String cost;

        /**
         * Constructor to initialize an event with all its details.
         *
         * @param eventCode Unique identifier for the event.
         * @param eventName Name of the event.
         * @param description Short description of the event.
         * @param location Venue where the event is held.
         * @param dateTime Date and time of the event.
         * @param capacity Maximum number of attendees allowed.
         * @param cost Cost of attending the event (e.g., Free or Paid).
         */
        public Event(String eventCode, String eventName, String description, String location, String dateTime, String capacity, String cost) {
            this.eventCode = eventCode;
            this.eventName = eventName;
            this.description = description;
            this.location = location;
            this.dateTime = dateTime;
            this.capacity = capacity;
            this.cost = cost;
        }

        // Getters for each attribute, allowing the TableView to access event details
        public String getEventCode() { return eventCode; }
        public String getEventName() { return eventName; }
        public String getDescription() { return description; }
        public String getLocation() { return location; }
        public String getDateTime() { return dateTime; }
        public String getCapacity() { return capacity; }
        public String getCost() { return cost; }
    }
}
