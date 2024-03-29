package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseOtherPackages {
    @SerializedName("Package_ID")
    @Expose
    private String packageID;
    @SerializedName("Mode")
    @Expose
    private String mode;
    @SerializedName("Batch_Strength")
    @Expose
    private String batchStrength;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("Sessions")
    @Expose
    private Integer sessions;
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("Discount")
    @Expose
    private Integer discount;
    @SerializedName("Discount_Type")
    @Expose
    private String discountType;

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBatchStrength() {
        return batchStrength;
    }

    public void setBatchStrength(String batchStrength) {
        this.batchStrength = batchStrength;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSessions() {
        return sessions;
    }

    public void setSessions(Integer sessions) {
        this.sessions = sessions;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
}
