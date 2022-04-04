package com.example.feelthenote.Network;

public class ProfileImageChangeRequest {
    private String image_name;
    private String Image_Date;
    private String Image_ID;
    private byte[] Image;
    int Object_ID;

    public ProfileImageChangeRequest(String image_name, String image_Date, String image_ID, byte[] image, int object_ID) {
        this.image_name = image_name;
        Image_Date = image_Date;
        Image_ID = image_ID;
        Image = image;
        Object_ID = object_ID;
    }

    public String getProfileImageName() {
        return image_name;
    }

    public void setProfileImageName(String profileImageName) {
        this.image_name = profileImageName;
    }

    public byte[] getProfileImage() {
        return Image;
    }

    public void setProfileImage(byte[] profileImage) {
        this.Image = profileImage;
    }

    public String getImage_Date() {
        return Image_Date;
    }

    public void setImage_Date(String image_Date) {
        Image_Date = image_Date;
    }

    public String getImage_ID() {
        return Image_ID;
    }

    public void setImage_ID(String image_ID) {
        Image_ID = image_ID;
    }

    public int getObject_ID() {
        return Object_ID;
    }

    public void setObject_ID(int object_ID) {
        Object_ID = object_ID;
    }
}
