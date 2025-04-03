package com.example.universitymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String studentId;
    private String name;
    private String email;
    private String academicLevel;
    private String currentSemester;
    private List<StudentCourse> enrolledCourses;

    // Updated Student constructor
    public Student(String username, String password, String studentId, String name, String email, String academicLevel, String currentSemester) {
        super(username, password); // Call the parent (User) constructor
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.academicLevel = academicLevel;
        this.currentSemester = currentSemester;
        this.enrolledCourses = new ArrayList<>(); // Initialize the list of enrolled courses
    }

    // Getters and setters for Student properties

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {  //  Missing Getter: Added back
        return email;
    }

    public String getAcademicLevel() {  //  Missing Getter: Added back
        return academicLevel;
    }

    public String getCurrentSemester() {  //  Missing Getter: Added back
        return currentSemester;
    }

    // Enroll a student in a course
    public void enrollInCourse(StudentCourse course) {
        enrolledCourses.add(course);
    }

    // Get list of enrolled courses
    public List<StudentCourse> getEnrolledCourses() {
        return enrolledCourses;
    }
}
