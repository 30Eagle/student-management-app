package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_classes")
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_class_id")
    private Long studentClassId;

    // Many students can join many classes
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserStudent student;

    // Each class belongs to one teacher
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserTeacher teacher;

    // Reference to the join code used to join this class
    @ManyToOne
    @JoinColumn(name = "join_code_id", nullable = false)
    private JoinCode joinCode;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Constructors
    public StudentClass() {}

    public StudentClass(UserStudent student, UserTeacher teacher, JoinCode joinCode) {
        this.student = student;
        this.teacher = teacher;
        this.joinCode = joinCode;
        this.joinedAt = LocalDateTime.now();
        this.isActive = true;
    }

    // Getters and Setters
    public Long getStudentClassId() {
        return studentClassId;
    }

    public void setStudentClassId(Long studentClassId) {
        this.studentClassId = studentClassId;
    }

    public UserStudent getStudent() {
        return student;
    }

    public void setStudent(UserStudent student) {
        this.student = student;
    }

    public UserTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(UserTeacher teacher) {
        this.teacher = teacher;
    }

    public JoinCode getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(JoinCode joinCode) {
        this.joinCode = joinCode;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "studentClassId=" + studentClassId +
                ", student=" + student.getStudentName() +
                ", teacher=" + teacher.getTeacherName() +
                ", subject=" + joinCode.getSubject() +
                ", section=" + joinCode.getSectionNo() +
                ", joinedAt=" + joinedAt +
                ", isActive=" + isActive +
                '}';
    }
}