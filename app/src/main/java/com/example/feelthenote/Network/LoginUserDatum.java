package com.example.feelthenote.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUserDatum {
    @SerializedName("User_ID")
    @Expose
    private Integer userID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Contact1")
    @Expose
    private String contact1;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("User_Type")
    @Expose
    private Integer userType;
    @SerializedName("Is_Active")
    @Expose
    private Boolean isActive;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
