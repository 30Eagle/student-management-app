package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "join_codes")
public class JoinCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_code_id")
    private Long joinCodeId;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "section_no", nullable = false)
    private String sectionNo;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Foreign key mapping to teacher
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserTeacher teacher;

    // Constructors
    public JoinCode() {}

    public JoinCode(String code, String subject, String sectionNo, String description, UserTeacher teacher) {
        this.code = code;
        this.subject = subject;
        this.sectionNo = sectionNo;
        this.description = description;
        this.teacher = teacher;
        this.isActive = true;
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

    public UserTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(UserTeacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "JoinCode{" +
                "joinCodeId=" + joinCodeId +
                ", code='" + code + '\'' +
                ", subject='" + subject + '\'' +
                ", sectionNo='" + sectionNo + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", teacher=" + teacher.getTeacherName() +
                '}';
    }
}