package com.example.feelthenote;

import android.graphics.drawable.Drawable;

public class Course {
    int totalAllotedSessions, attended, pending, available, expired, daysLeft, extraHrs;
    String courseName, courseCode;
    String instructor;
    Drawable courseImage;

    public Course(int totalAllotedSessions, int attended, int pending, int available, int expired, int daysLeft, int extraHrs, String courseName, String courseCode, String instructor, Drawable courseImage) {
        this.totalAllotedSessions = totalAllotedSessions;
        this.attended = attended;
        this.pending = pending;
        this.available = available;
        this.expired = expired;
        this.daysLeft = daysLeft;
        this.extraHrs = extraHrs;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        this.courseImage = courseImage;
    }

    public int getTotalAllotedSessions() {
        return totalAllotedSessions;
    }

    public void setTotalAllotedSessions(int totalAllotedSessions) {
        this.totalAllotedSessions = totalAllotedSessions;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public int getExtraHrs() {
        return extraHrs;
    }

    public void setExtraHrs(int extraHrs) {
        this.extraHrs = extraHrs;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Drawable getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(Drawable courseImage) {
        this.courseImage = courseImage;
    }
}
