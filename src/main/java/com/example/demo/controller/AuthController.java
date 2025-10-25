package com.example.demo.controller;

import com.example.demo.dto.TeacherRegistrationRequest;
import com.example.demo.dto.StudentRegistrationRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.UserTeacher;
import com.example.demo.model.UserStudent;
import com.example.demo.service.TeacherService;
import com.example.demo.service.StudentService;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TeacherRepository teacherRepo, StudentRepository studentRepo,
                         TeacherService teacherService, StudentService studentService,
                         JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // TEACHER ENDPOINTS
    @PostMapping("/register-teacher")
    public UserTeacher registerTeacher(@RequestBody TeacherRegistrationRequest request) {
        return teacherService.save(request);
    }

    @PostMapping("/login-teacher")
    public AuthResponse loginTeacher(@RequestBody TeacherRegistrationRequest request) {
        UserTeacher teacher = teacherRepo.findByTeacherName(request.getTeacherName())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(request.getPassword(), teacher.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(teacher.getTeacherName());
        return new AuthResponse(token);
    }

    // STUDENT ENDPOINTS
    @PostMapping("/register-student")
    public UserStudent registerStudent(@RequestBody StudentRegistrationRequest request) {
        return studentService.registerStudent(request);
    }

    @PostMapping("/login-student")
    public AuthResponse loginStudent(@RequestBody StudentRegistrationRequest request) {
        UserStudent student = studentRepo.findByStudentName(request.getStudentName())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(student.getStudentName());
        return new AuthResponse(token);
    }
}