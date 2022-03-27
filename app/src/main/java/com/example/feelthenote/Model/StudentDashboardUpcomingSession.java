package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDashboardUpcomingSession {
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("Course_ID")
    @Expose
    private String courseID;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("Instructor")
    @Expose
    private String instructorName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorName() { return instructorName; }

    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}
