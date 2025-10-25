package com.example.demo.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TeacherRegistrationRequest;
import com.example.demo.model.UserTeacher;
import com.example.demo.model.UserStudent;
import com.example.demo.service.TeacherService;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "*") 
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;

    public TeacherController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    // Remove this - move to AuthController
    // @PostMapping
    // public UserTeacher createTeacher(@RequestBody TeacherRegistrationRequest teacher) {
    //     return teacherService.save(teacher);
    // }

    // Get my profile (authenticated teacher only)
    @GetMapping("/profile")
    public UserTeacher getMyProfile(Authentication auth) {
        String teacherName = auth.getName();
        return teacherService.getByName(teacherName);
    }

    // Get my students (authenticated teacher only)
    @GetMapping("/my-students")
    public List<UserStudent> getMyStudents(Authentication auth) {
        String teacherName = auth.getName();
        UserTeacher teacher = teacherService.getByName(teacherName);
        return studentService.getStudentsByTeacher(teacher.getTeacherId());
    }

    // Update my profile (authenticated teacher only)
    @PutMapping("/profile")
    public ResponseEntity<UserTeacher> updateMyProfile(@RequestBody TeacherRegistrationRequest updatedTeacher, Authentication auth) {
        String teacherName = auth.getName();
        UserTeacher teacher = teacherService.updateProfile(teacherName, updatedTeacher);
        return ResponseEntity.ok(teacher);
    }

    // Get all teachers (admin endpoint)
    @GetMapping("/all")
    public List<UserTeacher> getAllTeachers() {
        return teacherService.getAll();
    }

    // Get teacher by ID (admin endpoint)
    @GetMapping("/{id}")
    public ResponseEntity<UserTeacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update teacher by ID (admin endpoint)
    @PutMapping("/{id}")
    public ResponseEntity<UserTeacher> updateTeacher(@PathVariable Long id, @RequestBody TeacherRegistrationRequest updatedTeacher) {
        return teacherService.getById(id).map(existingTeacher -> {
            updatedTeacher.setId(id);
            return ResponseEntity.ok(teacherService.save(updatedTeacher));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete teacher (admin endpoint)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        if (teacherService.getById(id).isPresent()) {
            teacherService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}