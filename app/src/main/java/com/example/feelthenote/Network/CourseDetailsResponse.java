package com.example.feelthenote.Network;

import com.example.feelthenote.Model.CourseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetailsResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("CourseData")
    @Expose
    private CourseData courseData;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public CourseData getCourseData() {
        return courseData;
    }

    public void setCourseData(CourseData courseData) {
        this.courseData = courseData;
    }
}
