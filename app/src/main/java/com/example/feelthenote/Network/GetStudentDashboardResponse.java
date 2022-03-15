package com.example.feelthenote.Network;

import com.example.feelthenote.Model.StudentDashboardData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStudentDashboardResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("StudentDashboardData")
    @Expose
    private StudentDashboardData studentDashboardData;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public StudentDashboardData getStudentDashboardData() {
        return studentDashboardData;
    }

    public void setStudentDashboardData(StudentDashboardData studentDashboardData) {
        this.studentDashboardData = studentDashboardData;
    }
}
