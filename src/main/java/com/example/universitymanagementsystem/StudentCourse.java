package com.example.universitymanagementsystem;

public class StudentCourse {
    private String courseId;
    private String courseName;
    private int credits;
    private double gradePoints;
    private boolean isCompleted; // ✅ Added

    public StudentCourse(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.gradePoints = 0.0;
        this.isCompleted = false; // Default to not completed
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public double getGradePoints() { return gradePoints; }
    public void setGradePoints(double gradePoints) { this.gradePoints = gradePoints; }

    public boolean isCompleted() { return isCompleted; }  // ✅ Fix: Added method
    public void setCompleted(boolean completed) { this.isCompleted = completed; } // ✅ Setter

    private boolean isEnrolled; // New field

    public boolean isEnrolled() { return isEnrolled; }
    public void setEnrolled(boolean enrolled) { this.isEnrolled = enrolled; }

}
