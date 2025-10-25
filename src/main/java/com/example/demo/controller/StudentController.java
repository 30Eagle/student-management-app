package com.example.demo.controller;

import com.example.demo.dto.JoinClassRequest;
import com.example.demo.dto.StudentRegistrationRequest;
import com.example.demo.dto.StudentClassResponse;
import com.example.demo.dto.TeacherSummaryResponse;
import com.example.demo.model.UserStudent;
import com.example.demo.model.StudentClass;
import com.example.demo.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

 // Update this in your StudentController.java

 // Change this endpoint from "/join" to "/join-class"
 @PostMapping("/join-class")  // Changed from "/join"
 public ResponseEntity<?> joinClass(@RequestBody JoinClassRequest request, Authentication auth) {
     try {
         String studentName = auth.getName();
         StudentClass studentClass = studentService.joinClass(studentName, request.getJoinCode());
         
         return ResponseEntity.ok("Successfully joined class: " + 
             studentClass.getJoinCode().getSubject() + " - Section " + 
             studentClass.getJoinCode().getSectionNo());
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error: " + e.getMessage());
     }
 }

    // Leave a class
    @PostMapping("/leave-class")
    public ResponseEntity<?> leaveClass(@RequestBody JoinClassRequest request, Authentication auth) {
        try {
            String studentName = auth.getName();
            studentService.leaveClass(studentName, request.getJoinCode());
            return ResponseEntity.ok("Successfully left the class");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get my profile (authenticated student only)
    @GetMapping("/profile")
    public UserStudent getMyProfile(Authentication auth) {
        String studentName = auth.getName();
        return studentService.getByName(studentName);
    }

    // Get all my teachers (summary)
    @GetMapping("/my-teachers")
    public List<TeacherSummaryResponse> getMyTeachers(Authentication auth) {
        String studentName = auth.getName();
        return studentService.getMyTeachers(studentName);
    }

    // Get all my classes (detailed)
    @GetMapping("/my-classes")
    public List<StudentClassResponse> getMyClasses(Authentication auth) {
        String studentName = auth.getName();
        return studentService.getMyClasses(studentName);
    }

    // Get classmates for a specific class
    @GetMapping("/classmates/{joinCode}")
    public List<UserStudent> getClassmates(@PathVariable String joinCode, Authentication auth) {
        String studentName = auth.getName();
        return studentService.getClassmates(studentName, joinCode);
    }

    // Get students by teacher (for teachers/admins)
    @GetMapping("/teacher/{teacherId}")
    public List<UserStudent> getStudentsByTeacher(@PathVariable Long teacherId) {
        return studentService.getStudentsByTeacher(teacherId);
    }

    // Get all students (admin only)
    @GetMapping("/all")
    public List<UserStudent> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Deprecated endpoints (kept for backward compatibility)
    @GetMapping("/my-teacher")
    @Deprecated
    public ResponseEntity<?> getMyTeacher(Authentication auth) {
        return ResponseEntity.ok("Please use /my-teachers endpoint instead");
    }

    @GetMapping("/classmates")
    @Deprecated
    public ResponseEntity<?> getClassmates(Authentication auth) {
        return ResponseEntity.ok("Please use /classmates/{joinCode} endpoint instead");
    }
}