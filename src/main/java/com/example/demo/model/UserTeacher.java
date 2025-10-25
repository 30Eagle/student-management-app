package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_teacher")
public class UserTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "teacher_name", nullable = false, unique = true)
    private String teacherName;
    
    @Column(name = "password", nullable = false)
    private String password;

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public UserTeacher() {}

    public UserTeacher(String teacherName) {
        this.teacherName = teacherName;
    }

    // getters & setters
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    @Override
    public String toString() {
        return "UserTeacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}