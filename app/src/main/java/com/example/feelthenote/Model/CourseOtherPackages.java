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
    private Integer batchStrength;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("Sessions")
    @Expose
    private Integer sessions;
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("Discount_Offer")
    @Expose
    private Integer discountOffer;
    @SerializedName("Total_Payable")
    @Expose
    private Integer totalPayable;

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

    public Integer getBatchStrength() {
        return batchStrength;
    }

    public void setBatchStrength(Integer batchStrength) {
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

    public Integer getDiscountOffer() {
        return discountOffer;
    }

    public void setDiscountOffer(Integer discountOffer) {
        this.discountOffer = discountOffer;
    }

    public Integer getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Integer totalPayable) {
        this.totalPayable = totalPayable;
    }
}
