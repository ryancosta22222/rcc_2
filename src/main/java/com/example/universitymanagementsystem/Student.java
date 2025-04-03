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
    private List<Grade> grades;
    private double gpa;

    public Student(String username, String password, String studentId, String name, String email, String academicLevel, String currentSemester) {
        super(username, password);
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.academicLevel = academicLevel;
        this.currentSemester = currentSemester;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.gpa = 0.0;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAcademicLevel() { return academicLevel; }
    public String getCurrentSemester() { return currentSemester; }
    public List<StudentCourse> getEnrolledCourses() { return enrolledCourses; }
    public double getGpa() { return gpa; }

    public void enrollInCourse(StudentCourse course) {
        // Prevent duplicate enrollment
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void unenrollFromCourse(String courseId) {
        enrolledCourses.removeIf(course -> course.getCourseId().equals(courseId));
    }

    public void addGrade(String courseName, double gradeValue) {
        grades.add(new Grade(courseName, gradeValue));
        updateGPA();
    }

    public void updateGPA() {
        if (grades.isEmpty()) {
            gpa = 0.0;
            return;
        }
        double totalPoints = 0;
        for (Grade grade : grades) {
            totalPoints += grade.getGrade();
        }
        gpa = totalPoints / grades.size();
    }

    public boolean isEligibleForGraduation() {
        int requiredCredits = academicLevel.equalsIgnoreCase("undergraduate") ? 120 : 60;
        int completedCredits = 0;

        for (StudentCourse course : enrolledCourses) {
            if (course.isCompleted()) {
                completedCredits += course.getCredits();
            }
        }
        return completedCredits >= requiredCredits;
    }

    public String getGraduationStatus() {
        return isEligibleForGraduation() ? "Eligible for Graduation" : "Not Eligible";
    }
}
