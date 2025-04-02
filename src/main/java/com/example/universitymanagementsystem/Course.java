package com.example.universitymanagementsystem;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {
    private String courseCode;
    private String courseName;
    private String subjectCode;
    private String section;
    private int capacity;
    private String lectureTime;
    private String finalExamDateTime;
    private String location;
    private String teacherName;
    private ObservableList<String> enrolledStudents = FXCollections.observableArrayList();

    // Integer property to track the number of enrolled students
    private final IntegerProperty enrolledStudentCount = new SimpleIntegerProperty(this, "enrolledStudentCount", 0);

    public Course(String courseCode, String courseName, String subjectCode, String section,
                  int capacity, String lectureTime, String finalExamDateTime, String location, String teacherName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectCode = subjectCode;
        this.section = section;
        this.capacity = capacity;
        this.lectureTime = lectureTime;
        this.finalExamDateTime = finalExamDateTime;
        this.location = location;
        this.teacherName = teacherName;
    }

    // Getters
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getSubjectCode() { return subjectCode; }
    public String getSection() { return section; }
    public int getCapacity() { return capacity; }
    public String getLectureTime() { return lectureTime; }
    public String getFinalExamDateTime() { return finalExamDateTime; }
    public String getLocation() { return location; }
    public String getTeacherName() { return teacherName; }

    // Setters
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public void setSection(String section) { this.section = section; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setLectureTime(String lectureTime) { this.lectureTime = lectureTime; }
    public void setFinalExamDateTime(String finalExamDateTime) { this.finalExamDateTime = finalExamDateTime; }
    public void setLocation(String location) { this.location = location; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    // Enrollment management methods
    public ObservableList<String> getEnrolledStudents() { return enrolledStudents; }

    // Method to enroll a student
    public void enrollStudent(String studentId) {
        if (!enrolledStudents.contains(studentId) && enrolledStudents.size() < capacity) {
            enrolledStudents.add(studentId);
            enrolledStudentCount.set(enrolledStudents.size());  // Update the enrolled student count
        }
    }

    // Method to remove a student
    public void removeStudent(String studentId) {
        enrolledStudents.remove(studentId);
        enrolledStudentCount.set(enrolledStudents.size());  // Update the enrolled student count
    }

    // Method to get the enrolled student count property
    public IntegerProperty enrolledStudentCountProperty() {
        return enrolledStudentCount;
    }

    // Method to get the current count of enrolled students
    public int getEnrolledStudentCount() {
        return enrolledStudentCount.get();
    }
}

