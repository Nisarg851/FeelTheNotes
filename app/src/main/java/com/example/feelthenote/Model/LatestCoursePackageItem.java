package com.example.feelthenote.Model;

public class LatestCoursePackageItem {
    private int transactionNumber;
    private String packageId, expiryDate;

    public LatestCoursePackageItem(int transactionNumber, String packageId, String expiryDate) {
        this.transactionNumber = transactionNumber;
        this.packageId = packageId;
        this.expiryDate = expiryDate;
    }
}
