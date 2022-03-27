package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDashboardCourseCarousel {
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("Regular")
    @Expose
    private Integer regular;
    @SerializedName("Carried")
    @Expose
    private Integer carried;
    @SerializedName("Extra")
    @Expose
    private Integer extra;
    @SerializedName("Booked")
    @Expose
    private Integer booked;
    @SerializedName("Attended")
    @Expose
    private Integer attended;
    @SerializedName("Cancelled")
    @Expose
    private Integer cancelled;
    @SerializedName("Missed")
    @Expose
    private Integer missed;
    @SerializedName("Expired")
    @Expose
    private Integer expired;
    @SerializedName("Transaction_No")
    @Expose
    private Integer transactionNo;
    @SerializedName("Course_ID")
    @Expose
    private String courseID;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("Card_Image")
    @Expose
    private String cardImage;
    @SerializedName("Is_Active")
    @Expose
    private Boolean isActive;
    @SerializedName("Course_Instructor")
    @Expose
    private String courseInstructor;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    public Integer getCarried() {
        return carried;
    }

    public void setCarried(Integer carried) {
        this.carried = carried;
    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }

    public Integer getBooked() {
        return booked;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }

    public Integer getAttended() {
        return attended;
    }

    public void setAttended(Integer attended) {
        this.attended = attended;
    }

    public Integer getCancelled() {
        return cancelled;
    }

    public void setCancelled(Integer cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getMissed() {
        return missed;
    }

    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Integer transactionNo) {
        this.transactionNo = transactionNo;
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

}
