package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseFacultyDetails{
    @SerializedName("Faculty_ID")
    @Expose
    private Integer facultyID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("About")
    @Expose
    private String about;
    @SerializedName("Profile_Image_Date")
    @Expose
    private String facultyProfileImageDate;

    public Integer getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(Integer facultyID) {
        this.facultyID = facultyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFacultyProfileImageDate() {
        return facultyProfileImageDate;
    }

    public void setFacultyProfileImageDate(String facultyProfileImageDate) {
        this.facultyProfileImageDate = facultyProfileImageDate;
    }

}
