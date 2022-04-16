package com.example.feelthenote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionData {
    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("Session_ID")
    @Expose
    private Integer sessionID;
    @SerializedName("Student_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Student_Mapping_ID")
    @Expose
    private Integer studentMappingID;
    @SerializedName("Faculty_ID")
    @Expose
    private Integer facultyID;
    @SerializedName("Faculty_Mapping_ID")
    @Expose
    private Integer facultyMappingID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Note")
    @Expose
    private Object note;
    @SerializedName("Slot_Altered_Notes")
    @Expose
    private String slotAlteredNotes;
    @SerializedName("Instructor_Conductor_ID")
    @Expose
    private Object instructorConductorID;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getStudentMappingID() {
        return studentMappingID;
    }

    public void setStudentMappingID(Integer studentMappingID) {
        this.studentMappingID = studentMappingID;
    }

    public Integer getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(Integer facultyID) {
        this.facultyID = facultyID;
    }

    public Integer getFacultyMappingID() {
        return facultyMappingID;
    }

    public void setFacultyMappingID(Integer facultyMappingID) {
        this.facultyMappingID = facultyMappingID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getSlotAlteredNotes() {
        return slotAlteredNotes;
    }

    public void setSlotAlteredNotes(String slotAlteredNotes) {
        this.slotAlteredNotes = slotAlteredNotes;
    }

    public Object getInstructorConductorID() {
        return instructorConductorID;
    }

    public void setInstructorConductorID(Object instructorConductorID) {
        this.instructorConductorID = instructorConductorID;
    }
}
