// JoinCodeCreateRequest.java
package com.example.demo.dto;

public class JoinCodeCreateRequest {
    private String code;
    private String subject;
    private String sectionNo;
    private String description;

    // Constructors
    public JoinCodeCreateRequest() {}

    public JoinCodeCreateRequest(String code, String subject, String sectionNo, String description) {
        this.code = code;
        this.subject = subject;
        this.sectionNo = sectionNo;
        this.description = description;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


  