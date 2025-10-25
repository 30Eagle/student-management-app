package com.example.demo.repository; // Changed from "Repo" to "repository"

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.TeacherRegistrationRequest;
import com.example.demo.model.UserTeacher;

@Repository
public interface TeacherRepository extends JpaRepository<UserTeacher, Long> {
    Optional<UserTeacher> findByTeacherName(String teacherName); // Add this method


	
}