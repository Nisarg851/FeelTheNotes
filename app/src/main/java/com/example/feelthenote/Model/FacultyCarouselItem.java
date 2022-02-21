package com.example.feelthenote.Model;

import android.graphics.drawable.Drawable;

public class FacultyCarouselItem {
    private String facultyName, facultyAbout;
    private Drawable facultyImage;

    public FacultyCarouselItem(String facultyName, String facultyAbout, Drawable facultyImage) {
        this.facultyName = facultyName;
        this.facultyAbout = facultyAbout;
        this.facultyImage = facultyImage;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyAbout() {
        return facultyAbout;
    }

    public void setFacultyAbout(String facultyAbout) {
        this.facultyAbout = facultyAbout;
    }

    public Drawable getFacultyImage() {
        return facultyImage;
    }

    public void setFacultyImage(Drawable facultyImage) {
        this.facultyImage = facultyImage;
    }
}
