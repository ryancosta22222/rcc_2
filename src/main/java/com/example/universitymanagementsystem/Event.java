package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a single Event in the system.
 */
public class Event {
    private String eventCode;
    private String eventName;
    private String description;
    private String headerImagePath; // path or URL to the event header image
    private String location;
    private String dateTime;
    private int capacity;
    private String cost; // e.g. "Free" or "Paid ($20)"

    // Track who registered (in this simple example, store just the student username)
    private final ObservableList<String> registeredStudents = FXCollections.observableArrayList();

    public Event(String eventCode, String eventName, String description, String headerImagePath,
                 String location, String dateTime, int capacity, String cost) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.description = description;
        this.headerImagePath = headerImagePath;
        this.location = location;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.cost = cost;
    }

    // Getters and Setters
    public String getEventCode() { return eventCode; }
    public void setEventCode(String eventCode) { this.eventCode = eventCode; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHeaderImagePath() { return headerImagePath; }
    public void setHeaderImagePath(String headerImagePath) { this.headerImagePath = headerImagePath; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getCost() { return cost; }
    public void setCost(String cost) { this.cost = cost; }

    public ObservableList<String> getRegisteredStudents() {
        return registeredStudents;
    }

    /**
     * Checks if there's room for another registration based on capacity.
     */
    public boolean canRegister() {
        return registeredStudents.size() < capacity;
    }
}
