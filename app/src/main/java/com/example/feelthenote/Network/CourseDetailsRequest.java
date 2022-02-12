package com.example.feelthenote.Network;

public class CourseDetailsRequest {
    private int Student_ID;
    private String Course_ID;

    public CourseDetailsRequest(int student_ID, String course_ID) {
        Student_ID = student_ID;
        Course_ID = course_ID;
    }
}
