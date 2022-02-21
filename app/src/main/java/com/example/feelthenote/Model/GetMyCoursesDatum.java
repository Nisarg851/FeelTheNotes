package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyCoursesDatum {
    @SerializedName("Course_ID")
    @Expose
    private String courseID;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("Card_Image")
    @Expose
    private String cardImage;
    @SerializedName("Subscription")
    @Expose
    private String subscription;

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

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
