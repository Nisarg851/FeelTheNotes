package com.example.feelthenote.Network;

public class ProfileImageChangeRequest {
    private String profileImageName;
    private byte[] profileImage;

    public ProfileImageChangeRequest(String profileImageName, byte[] profileImage) {
        this.profileImageName = profileImageName;
        this.profileImage = profileImage;
    }
    public String getProfileImageName() {
        return profileImageName;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
