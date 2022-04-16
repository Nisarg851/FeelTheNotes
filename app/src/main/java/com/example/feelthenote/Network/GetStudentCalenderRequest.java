package com.example.feelthenote.Network;

public class GetStudentCalenderRequest {
    int Student_ID, Student_Mapping_ID;
    String From_Date, To_Date, Status;

    public GetStudentCalenderRequest(int student_ID, int student_Mapping_ID, String from_Date, String to_Date, String status) {
        Student_ID = student_ID;
        Student_Mapping_ID = student_Mapping_ID;
        From_Date = from_Date;
        To_Date = to_Date;
        Status = status;
    }
}
