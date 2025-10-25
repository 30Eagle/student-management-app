package com.example.demo.repository;

import com.example.demo.model.StudentClass;
import com.example.demo.model.UserStudent;
import com.example.demo.model.UserTeacher;
import com.example.demo.model.JoinCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    
    // Find all classes for a specific student
    List<StudentClass> findByStudentAndIsActive(UserStudent student, Boolean isActive);
    
    // Find all students in a specific class (by join code)
    List<StudentClass> findByJoinCodeAndIsActive(JoinCode joinCode, Boolean isActive);
    
    // Find all students for a specific teacher
    List<StudentClass> findByTeacherAndIsActive(UserTeacher teacher, Boolean isActive);
    
    // Check if student is already enrolled in a specific class
    Optional<StudentClass> findByStudentAndJoinCode(UserStudent student, JoinCode joinCode);
    
    // Find all active classes for a student by student ID
    @Query("SELECT sc FROM StudentClass sc WHERE sc.student.studentId = :studentId AND sc.isActive = true")
    List<StudentClass> findActiveClassesByStudentId(@Param("studentId") Long studentId);
    
    // Find all students in same class as given student (classmates)
    @Query("SELECT sc FROM StudentClass sc WHERE sc.joinCode.joinCodeId = :joinCodeId AND sc.isActive = true")
    List<StudentClass> findClassmatesByJoinCodeId(@Param("joinCodeId") Long joinCodeId);
    
    // Find all classes for a teacher by teacher ID
    @Query("SELECT sc FROM StudentClass sc WHERE sc.teacher.teacherId = :teacherId AND sc.isActive = true")
    List<StudentClass> findActiveClassesByTeacherId(@Param("teacherId") Long teacherId);
}