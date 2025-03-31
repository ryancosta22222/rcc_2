package com.example.universitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseDataStore {
    private static final ObservableList<Course> courses = FXCollections.observableArrayList();

    static {
        // Initialize sample courses (only once)
        courses.add(new Course("001", "Calculus I", "MATH001", "Section 1", 30,
                "Mon/Wed 9-11 AM", "12/15/2025 9:00", "Room 101", "Dr. Alan Turing"));
        courses.add(new Course("002", "Literature Basics", "ENG101", "Section 1", 25,
                "Tue/Thu 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"));
        courses.add(new Course("003", "Introduction to Programming", "CS201", "Section 1", 42,
                "Tue/Thu 12-2 PM", "12/16/2025 12:30", "Room 103", "Prof. Bahar Nozari"));
    }

    public static ObservableList<Course> getCourses() {
        return courses;
    }
}
