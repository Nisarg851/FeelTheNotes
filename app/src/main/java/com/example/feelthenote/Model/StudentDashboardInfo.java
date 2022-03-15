package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDashboardInfo {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Profile_Image_Date")
    @Expose
    private String profileImageDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageDate() {
        return profileImageDate;
    }

    public void setProfileImageDate(String profileImageDate) {
        this.profileImageDate = profileImageDate;
    }
}
