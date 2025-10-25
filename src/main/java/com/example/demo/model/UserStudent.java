package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_student")
public class UserStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;
    
    @Column(name = "password", nullable = false)
    private String password;

    // Make teacher assignment optional - student can register without a teacher
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = true) // Changed to nullable = true
    private UserTeacher teacher;

    public UserStudent() {}

    // Constructor without teacher (for registration)
    public UserStudent(String studentName) {
        this.studentName = studentName;
    }

    // Constructor with teacher (for joining classes later)
    public UserStudent(String studentName, UserTeacher teacher) {
        this.studentName = studentName;
        this.teacher = teacher;
    }

    // Getters & Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTeacher getTeacher() { return teacher; }
    public void setTeacher(UserTeacher teacher) { this.teacher = teacher; }

    @Override
    public String toString() {
        return "UserStudent{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", teacher=" + (teacher != null ? teacher.getTeacherName() : "No teacher assigned") +
                '}';
    }
}