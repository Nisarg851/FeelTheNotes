package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseFilter {
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("Student_Mapping_ID")
    @Expose
    private Integer studentMappingID;
    @SerializedName("Faculty_Mapping_ID")
    @Expose
    private Integer facultyMappingID;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getStudentMappingID() {
        return studentMappingID;
    }

    public void setStudentMappingID(Integer studentMappingID) {
        this.studentMappingID = studentMappingID;
    }

    public Integer getFacultyMappingID() {
        return facultyMappingID;
    }

    public void setFacultyMappingID(Integer facultyMappingID) {
        this.facultyMappingID = facultyMappingID;
    }
}
