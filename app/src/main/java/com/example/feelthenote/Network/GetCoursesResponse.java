package com.example.feelthenote.Network;

import com.example.feelthenote.Model.Courses;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCoursesResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("CourseData")
    @Expose
    private Courses courses;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }
}
