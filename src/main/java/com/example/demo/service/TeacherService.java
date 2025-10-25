package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.dto.TeacherRegistrationRequest;
import com.example.demo.model.UserTeacher;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Save or update a teacher
    public UserTeacher save(TeacherRegistrationRequest request) {
        UserTeacher teacher = new UserTeacher();
        teacher.setTeacherName(request.getTeacherName());
        teacher.setPassword(passwordEncoder.encode(request.getPassword()));

        return repo.save(teacher);
    }

    // Get all teachers
    public List<UserTeacher> getAll() {
        return repo.findAll();
    }

    // Get a teacher by ID
    public Optional<UserTeacher> getById(Long id) {
        return repo.findById(id);
    }

    // Get teacher by name (for authentication)
    public UserTeacher getByName(String teacherName) {
        return repo.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));
    }

    // Update teacher profile
    public UserTeacher updateProfile(String teacherName, TeacherRegistrationRequest updatedTeacher) {
        UserTeacher existingTeacher = getByName(teacherName);
        
        // Update only allowed fields
        existingTeacher.setTeacherName(updatedTeacher.getTeacherName());
        if (updatedTeacher.getPassword() != null && !updatedTeacher.getPassword().isEmpty()) {
            existingTeacher.setPassword(passwordEncoder.encode(updatedTeacher.getPassword()));
        }
        
        return repo.save(existingTeacher);
    }

    // Delete a teacher by ID
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}