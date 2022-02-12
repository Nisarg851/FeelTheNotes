package com.example.feelthenote.Model;

import com.example.feelthenote.Model.GetMyCoursesDatum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Courses {

    @SerializedName("Table")
    @Expose
    private List<GetMyCoursesDatum> getMyCoursesDatumList = null;
    @SerializedName("Table1")
    @Expose
    private List<GetExploreCoursesDatum> getExploreCoursesDatumList = null;

    public List<GetMyCoursesDatum> getMyCoursesDatumList() {
        return getMyCoursesDatumList;
    }

    public void setTable(List<GetMyCoursesDatum> getMyCoursesDatumList) {
        this.getMyCoursesDatumList = getMyCoursesDatumList;
    }

    public List<GetExploreCoursesDatum> getExploreCoursesDatumList() {
        return getExploreCoursesDatumList;
    }

    public void setTable1(List<GetExploreCoursesDatum> getExploreCoursesDatumList) {
        this.getExploreCoursesDatumList = getExploreCoursesDatumList;
    }

    }
