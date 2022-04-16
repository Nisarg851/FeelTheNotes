package com.example.feelthenote.Network;

import com.example.feelthenote.Model.StudentCalenderdData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStudentCalenderResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("StudentCalenderdData")
    @Expose
    private StudentCalenderdData studentCalenderdData;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public StudentCalenderdData getStudentCalenderdData() {
        return studentCalenderdData;
    }

    public void setStudentCalenderdData(StudentCalenderdData studentCalenderdData) {
        this.studentCalenderdData = studentCalenderdData;
    }
}
