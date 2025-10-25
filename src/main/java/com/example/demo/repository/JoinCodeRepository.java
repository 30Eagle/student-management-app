package com.example.demo.repository;

import com.example.demo.model.JoinCode;
import com.example.demo.model.UserTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinCodeRepository extends JpaRepository<JoinCode, Long> {
    
    // Find join code by code string
    Optional<JoinCode> findByCode(String code);
    
    // Find all join codes for a specific teacher
    List<JoinCode> findByTeacher(UserTeacher teacher);
    
    // Find active join codes for a teacher
    List<JoinCode> findByTeacherAndIsActive(UserTeacher teacher, Boolean isActive);
    
    // Find by teacher ID
    List<JoinCode> findByTeacher_TeacherId(Long teacherId);
    
    // Check if code exists
    boolean existsByCode(String code);
    
    // Find active join codes by teacher ID
    List<JoinCode> findByTeacher_TeacherIdAndIsActive(Long teacherId, Boolean isActive);
}