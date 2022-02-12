package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetExploreCoursesDatum {
    @SerializedName("Course_ID")
    @Expose
    private String courseID;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("Card_Image")
    @Expose
    private Object cardImage;

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

    public Object getCardImage() {
        return cardImage;
    }

    public void setCardImage(Object cardImage) {
        this.cardImage = cardImage;
    }
}
