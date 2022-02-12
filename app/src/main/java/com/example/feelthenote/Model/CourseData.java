package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseData {

    @SerializedName("Table")
    @Expose
    private List<CourseDetails> table = null;
    @SerializedName("Table1")
    @Expose
    private List<CourseFacultyDetails> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<CourseLatestPackages> table2 = null;
    @SerializedName("Table3")
    @Expose
    private List<CourseOtherPackages> table3 = null;

    public List<CourseDetails> getTable() {
        return table;
    }

    public void setTable(List<CourseDetails> table) {
        this.table = table;
    }

    public List<CourseFacultyDetails> getTable1() {
        return table1;
    }

    public void setTable1(List<CourseFacultyDetails> table1) {
        this.table1 = table1;
    }

    public List<CourseLatestPackages> getTable2() {
        return table2;
    }

    public void setTable2(List<CourseLatestPackages> table2) {
        this.table2 = table2;
    }

    public List<CourseOtherPackages> getTable3() {
        return table3;
    }

    public void setTable3(List<CourseOtherPackages> table3) {
        this.table3 = table3;
    }
}
