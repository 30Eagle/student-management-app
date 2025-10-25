package com.example.demo.dto;

public class TeacherSummaryResponse {
    private Long teacherId;
    private String teacherName;
    private String subject;
    private String sectionNo;
    private String joinCode;

    public TeacherSummaryResponse() {}

    public TeacherSummaryResponse(Long teacherId, String teacherName, String subject, 
                                String sectionNo, String joinCode) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subject = subject;
        this.sectionNo = sectionNo;
        this.joinCode = joinCode;
    }

    // Getters and Setters
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getSectionNo() { return sectionNo; }
    public void setSectionNo(String sectionNo) { this.sectionNo = sectionNo; }

    public String getJoinCode() { return joinCode; }
    public void setJoinCode(String joinCode) { this.joinCode = joinCode; }
    
}
