package com.example.universitymanagementsystem;

public class StudentCourse {
    private String courseId;
    private String courseName;
    private int credits;
    private double gradePoints;

    public StudentCourse(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.gradePoints = 0.0; // Default grade points, can be updated later
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public double getGradePoints() {
        return gradePoints;
    }

    public void setGradePoints(double gradePoints) {
        this.gradePoints = gradePoints;
    }
}
