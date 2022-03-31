package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertise {
    @SerializedName("Advertise_Image")
    @Expose
    private String advertiseImage;

    public String getAdvertiseImage() {
        return advertiseImage;
    }

    public void setAdvertiseImage(String advertiseImage) {
        this.advertiseImage = advertiseImage;
    }

}
