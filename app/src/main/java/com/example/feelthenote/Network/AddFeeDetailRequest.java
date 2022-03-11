package com.example.feelthenote.Network;

import java.math.BigInteger;

public class AddFeeDetailRequest {
    int Student_ID;
    String Course_ID;
    String Package_ID;
    String Promo_Code;
    String Start_Date;
    BigInteger Student_Mapping_ID;

    public AddFeeDetailRequest(int student_ID, String course_ID, String package_ID, String promo_Code, String start_Date, BigInteger student_Mapping_ID) {
        Student_ID = student_ID;
        Course_ID = course_ID;
        Package_ID = package_ID;
        Promo_Code = promo_Code;
        Start_Date = start_Date;
        Student_Mapping_ID = student_Mapping_ID;
    }
}
