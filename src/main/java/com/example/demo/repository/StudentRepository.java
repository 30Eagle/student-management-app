package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserStudent;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<UserStudent, Long> {
    List<UserStudent> findByTeacher_TeacherId(Long teacherId); // fetch all students for a teacher
    Optional<UserStudent> findByStudentName(String studentName); // Add this method

}
