package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentCalenderdData {
    @SerializedName("Table")
    @Expose
    private List<CourseFilter> table = null;
    @SerializedName("Table1")
    @Expose
    private List<SessionData> table1 = null;

    public List<CourseFilter> getCourseFilter() {
        return table;
    }

    public void setCourseFilter(List<CourseFilter> table) {
        this.table = table;
    }

    public List<SessionData> getSessionData() {
        return table1;
    }

    public void setSessionData(List<SessionData> table1) {
        this.table1 = table1;
    }
}

