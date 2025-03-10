package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

public class EventManagementController {

    @FXML private TableView<Event> eventTable;
    @FXML private TableColumn<Event, String> eventCodeColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> descriptionColumn;
    @FXML private TableColumn<Event, String> locationColumn;
    @FXML private TableColumn<Event, String> dateTimeColumn;
    @FXML private TableColumn<Event, String> capacityColumn;
    @FXML private TableColumn<Event, String> costColumn;

    @FXML private TextField eventCodeField;
    @FXML private TextField eventNameField;
    @FXML private TextField descriptionField;
    @FXML private TextField locationField;
    @FXML private TextField dateTimeField;
    @FXML private TextField capacityField;
    @FXML private TextField costField;

    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("eventCode"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        eventTable.setItems(eventList);

        // --- Sample Event Data ---
        eventList.addAll(
                new Event("EV001", "Welcome Seminar", "Orientation week", "Auditorium", "9/1/2025 10:00", "100", "Free"),
                new Event("EV002", "Research Workshop", "Graduate workshop", "Lab 301", "10/5/2025 14:00", "50", "Paid ($20)")
        );
    }

    @FXML
    private void handleAddEvent(){
        String code = eventCodeField.getText().trim();
        String name = eventNameField.getText().trim();
        String desc = descriptionField.getText().trim();
        String loc = locationField.getText().trim();
        String dt = dateTimeField.getText().trim();
        String cap = capacityField.getText().trim();
        String cost = costField.getText().trim();

        if(code.isEmpty() || name.isEmpty() || desc.isEmpty() || loc.isEmpty() || dt.isEmpty() || cap.isEmpty() || cost.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Missing Data");
            alert.setContentText("All event fields are required.");
            alert.showAndWait();
            return;
        }

        eventList.add(new Event(code, name, desc, loc, dt, cap, cost));
        eventCodeField.clear();
        eventNameField.clear();
        descriptionField.clear();
        locationField.clear();
        dateTimeField.clear();
        capacityField.clear();
        costField.clear();
    }

    public static class Event {
        private final String eventCode;
        private final String eventName;
        private final String description;
        private final String location;
        private final String dateTime;
        private final String capacity;
        private final String cost;

        public Event(String eventCode, String eventName, String description, String location, String dateTime, String capacity, String cost){
            this.eventCode = eventCode;
            this.eventName = eventName;
            this.description = description;
            this.location = location;
            this.dateTime = dateTime;
            this.capacity = capacity;
            this.cost = cost;
        }

        public String getEventCode(){ return eventCode; }
        public String getEventName(){ return eventName; }
        public String getDescription(){ return description; }
        public String getLocation(){ return location; }
        public String getDateTime(){ return dateTime; }
        public String getCapacity(){ return capacity; }
        public String getCost(){ return cost; }
    }
}
