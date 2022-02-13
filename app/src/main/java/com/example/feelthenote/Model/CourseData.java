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

    public List<CourseDetails> getCourseDetails() {
        return table;
    }

    public void setCourseDetails(List<CourseDetails> table) {
        this.table = table;
    }

    public List<CourseFacultyDetails> getCourseFacultyDetails() {
        return table1;
    }

    public void setCourseFacultyDetails(List<CourseFacultyDetails> table1) {
        this.table1 = table1;
    }

    public List<CourseLatestPackages> getCourseLatestPackages() {
        return table2;
    }

    public void setCourseLatestPackages(List<CourseLatestPackages> table2) {
        this.table2 = table2;
    }

    public List<CourseOtherPackages> getCourseOtherPackages() {
        return table3;
    }

    public void setCourseOtherPackages(List<CourseOtherPackages> table3) {
        this.table3 = table3;
    }
}
