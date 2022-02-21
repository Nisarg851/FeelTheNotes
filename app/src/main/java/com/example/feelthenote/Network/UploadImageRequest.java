package com.example.feelthenote.Network;

public class UploadImageRequest {

    private byte[] image;

    public UploadImageRequest(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
