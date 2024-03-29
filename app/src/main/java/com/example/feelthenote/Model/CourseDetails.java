package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetails {
    @SerializedName("Course_ID")
    @Expose
    private String courseID;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("About")
    @Expose
    private String about;
    @SerializedName("Detail")
    @Expose
    private String detail;
    @SerializedName("Background")
    @Expose
    private Object background;
    @SerializedName("Cover_Image_Update")
    @Expose
    private String coverImageDateTime;
    @SerializedName("Student_Mapping_ID")
    @Expose
    private Integer studentMappingID;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCoverImageDateTime() {
        return coverImageDateTime;
    }

    public void setCoverImageDateTime(String coverImageDateTime) {
        this.coverImageDateTime = coverImageDateTime;
    }

    public Object getBackground() {
        return background;
    }

    public void setBackground(Object background) {
        this.background = background;
    }

    public Integer getStudentMappingID() {
        return studentMappingID;
    }

    public void setStudentMappingID(Integer studentMappingID) {
        this.studentMappingID = studentMappingID;
    }
}
