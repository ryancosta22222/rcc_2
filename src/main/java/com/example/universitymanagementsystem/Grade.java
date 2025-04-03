package com.example.universitymanagementsystem;

public class Grade {
    private String course;
    private double grade;

    public Grade(String course, double grade) {
        this.course = course;
        this.grade = grade;
    }

    public String getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
