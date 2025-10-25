package com.example.demo.dto;

public class JoinCodeResponse {
    private Long joinCodeId;
    private String code;
    private String subject;
    private String sectionNo;
    private String description;
    private Boolean isActive;
    private Long teacherId;
    private String teacherName;

    // Constructors
    public JoinCodeResponse() {}

    public JoinCodeResponse(Long joinCodeId, String code, String subject, String sectionNo, 
                           String description, Boolean isActive, Long teacherId, String teacherName) {
        this.joinCodeId = joinCodeId;
        this.code = code;
        this.subject = subject;
        this.sectionNo = sectionNo;
        this.description = description;
        this.isActive = isActive;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    // Getters and Setters
    public Long getJoinCodeId() {
        return joinCodeId;
    }

    public void setJoinCodeId(Long joinCodeId) {
        this.joinCodeId = joinCodeId;
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}