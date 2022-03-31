package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentDashboardData {
    @SerializedName("Table")
    @Expose
    private List<StudentDashboardInfo> studentDashboardInfoList = null;
    @SerializedName("Table1")
    @Expose
    private List<StudentDashboardUpcomingSession> studentDashboardUpcomingSessionList = null;
    @SerializedName("Table2")
    @Expose
    private List<StudentDashboardCourseCarousel> studentDashboardCourseCarouselList = null;
    @SerializedName("Table3")
    @Expose
    private List<Advertise> advertise = null;

    public List<StudentDashboardInfo> getStudentDashboardInfo() {
        return studentDashboardInfoList;
    }

    public void setStudentDashboardInfo(List<StudentDashboardInfo> studentDashboardInfoList) {
        this.studentDashboardInfoList = studentDashboardInfoList;
    }

    public List<StudentDashboardUpcomingSession> getStudentDashboardUpcomingSession() {
        return studentDashboardUpcomingSessionList;
    }

    public void setStudentDashboardUpcomingSession(List<StudentDashboardUpcomingSession> studentDashboardUpcomingSessionList) {
        this.studentDashboardUpcomingSessionList = studentDashboardUpcomingSessionList;
    }

    public List<StudentDashboardCourseCarousel> getStudentDashboardCourseCarousel() {
        return studentDashboardCourseCarouselList;
    }

    public void setStudentDashboardCourseCarousel(List<StudentDashboardCourseCarousel> studentDashboardCourseCarouselList) {
        this.studentDashboardCourseCarouselList = studentDashboardCourseCarouselList;
    }

    public List<Advertise> getAdvertise() {
        return advertise;
    }

    public void setAdvertise(List<Advertise> advertise) {
        this.advertise = advertise;
    }
}
