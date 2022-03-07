package com.example.feelthenote.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCodeResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Discount")
    @Expose
    private Integer discount;
    @SerializedName("Discount_Type")
    @Expose
    private String discountType;
    @SerializedName("Status")
    @Expose
    private String status;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
