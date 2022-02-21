package com.example.feelthenote.Model;

import android.graphics.drawable.Drawable;

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
    @SerializedName("Image")
    @Expose
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
