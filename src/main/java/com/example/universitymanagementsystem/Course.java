package com.example.universitymanagementsystem;

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

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getSubjectCode() { return subjectCode; }
    public String getSection() { return section; }
    public int getCapacity() { return capacity; }
    public String getLectureTime() { return lectureTime; }
    public String getFinalExamDateTime() { return finalExamDateTime; }
    public String getLocation() { return location; }
    public String getTeacherName() { return teacherName; }
}
