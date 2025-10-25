package com.example.demo.dto;

public class StudentRegistrationRequest {
    private String studentName;
    private String password;
    
    // Constructors
    public StudentRegistrationRequest() {}
    
    public StudentRegistrationRequest(String studentName, String password) {
        this.studentName = studentName;
        this.password = password;
    }
    
    // Getters & Setters
    public String getStudentName() { 
        return studentName; 
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName; 
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}