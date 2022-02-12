package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseFacultyDetails {
    @SerializedName("Faculty_ID")
    @Expose
    private Integer facultyID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("About")
    @Expose
    private Object about;
    @SerializedName("Image")
    @Expose
    private Object image;

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

    public Object getAbout() {
        return about;
    }

    public void setAbout(Object about) {
        this.about = about;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

}
