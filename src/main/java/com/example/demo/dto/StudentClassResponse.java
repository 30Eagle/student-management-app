package com.example.demo.dto;

import java.time.LocalDateTime;

public class StudentClassResponse {
    private Long studentClassId;
    private String studentName;
    private Long studentId;
    private String teacherName;
    private Long teacherId;
    private String subject;
    private String sectionNo;
    private String joinCode;
    private String description;
    private LocalDateTime joinedAt;
    private Boolean isActive;

    // Constructors
    public StudentClassResponse() {}

    public StudentClassResponse(Long studentClassId, String studentName, Long studentId,
                              String teacherName, Long teacherId, String subject, 
                              String sectionNo, String joinCode, String description,
                              LocalDateTime joinedAt, Boolean isActive) {
        this.studentClassId = studentClassId;
        this.studentName = studentName;
        this.studentId = studentId;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.subject = subject;
        this.sectionNo = sectionNo;
        this.joinCode = joinCode;
        this.description = description;
        this.joinedAt = joinedAt;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getStudentClassId() { return studentClassId; }
    public void setStudentClassId(Long studentClassId) { this.studentClassId = studentClassId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getSectionNo() { return sectionNo; }
    public void setSectionNo(String sectionNo) { this.sectionNo = sectionNo; }

    public String getJoinCode() { return joinCode; }
    public void setJoinCode(String joinCode) { this.joinCode = joinCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}

