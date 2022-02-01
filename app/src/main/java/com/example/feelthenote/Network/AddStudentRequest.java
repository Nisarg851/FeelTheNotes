package com.example.feelthenote.Network;

public class AddStudentRequest {
    private String Name;
    private String Address;
    private String DOB;
    private String Gender;
    private String Prof_Study;
    private String Contact1;
    private String Contact2;
    private String Email;
    private String Reference;
    private String Password;

    public AddStudentRequest(String name, String address, String DOB, String gender, String prof, String contact1, String contact2, String email, String reference, String password) {
        Name = name;
        Address = address;
        this.DOB = DOB;
        Gender = gender;
        Prof_Study = prof;
        Contact1 = contact1;
        Contact2 = contact2;
        Email = email;
        Reference = reference;
        Password = password;
    }
}
