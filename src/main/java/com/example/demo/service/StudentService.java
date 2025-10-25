package com.example.demo.service;

import com.example.demo.dto.StudentRegistrationRequest;
import com.example.demo.dto.StudentClassResponse;
import com.example.demo.dto.TeacherSummaryResponse;
import com.example.demo.model.UserStudent;
import com.example.demo.model.JoinCode;
import com.example.demo.model.StudentClass;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.JoinCodeRepository;
import com.example.demo.repository.StudentClassRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final JoinCodeRepository joinCodeRepository;
    private final StudentClassRepository studentClassRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, 
                         JoinCodeRepository joinCodeRepository,
                         StudentClassRepository studentClassRepository,
                         PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.joinCodeRepository = joinCodeRepository;
        this.studentClassRepository = studentClassRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register student (no teacher assignment during registration)
    public UserStudent registerStudent(StudentRegistrationRequest request) {
        UserStudent student = new UserStudent();
        student.setStudentName(request.getStudentName());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        
        return studentRepository.save(student);
    }

    // Join a class using join code
    public StudentClass joinClass(String studentName, String joinCode) {
        // Find student
        UserStudent student = getByName(studentName);
        
        // Find join code
        JoinCode code = joinCodeRepository.findByCode(joinCode)
                .orElseThrow(() -> new RuntimeException("Invalid join code: " + joinCode));
        
        // Check if join code is active
        if (!code.getIsActive()) {
            throw new RuntimeException("Join code is inactive: " + joinCode);
        }
        
        // Check if student is already enrolled in this class
        if (studentClassRepository.findByStudentAndJoinCode(student, code).isPresent()) {
            throw new RuntimeException("You are already enrolled in this class");
        }
        
        // Create new student-class relationship
        StudentClass studentClass = new StudentClass(student, code.getTeacher(), code);
        return studentClassRepository.save(studentClass);
    }

    // Get all teachers for a student
    public List<TeacherSummaryResponse> getMyTeachers(String studentName) {
        UserStudent student = getByName(studentName);
        List<StudentClass> classes = studentClassRepository.findByStudentAndIsActive(student, true);
        
        return classes.stream()
                .map(sc -> new TeacherSummaryResponse(
                    sc.getTeacher().getTeacherId(),
                    sc.getTeacher().getTeacherName(),
                    sc.getJoinCode().getSubject(),
                    sc.getJoinCode().getSectionNo(),
                    sc.getJoinCode().getCode()
                ))
                .collect(Collectors.toList());
    }

    // Get my classes (detailed view)
    public List<StudentClassResponse> getMyClasses(String studentName) {
        UserStudent student = getByName(studentName);
        List<StudentClass> classes = studentClassRepository.findByStudentAndIsActive(student, true);
        
        return classes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get classmates for a specific class/join code
    public List<UserStudent> getClassmates(String studentName, String joinCode) {
        // Find the join code
        JoinCode code = joinCodeRepository.findByCode(joinCode)
                .orElseThrow(() -> new RuntimeException("Invalid join code: " + joinCode));
        
        // Get all students in this class
        List<StudentClass> classmates = studentClassRepository.findByJoinCodeAndIsActive(code, true);
        
        // Filter out the current student and return only UserStudent objects
        return classmates.stream()
                .map(StudentClass::getStudent)
                .filter(s -> !s.getStudentName().equals(studentName))
                .collect(Collectors.toList());
    }

    // Get all students for a teacher (updated for new relationship)
    public List<UserStudent> getStudentsByTeacher(Long teacherId) {
        List<StudentClass> classes = studentClassRepository.findActiveClassesByTeacherId(teacherId);
        
        return classes.stream()
                .map(StudentClass::getStudent)
                .distinct()
                .collect(Collectors.toList());
    }

    // Get student by name (for authentication)
    public UserStudent getByName(String studentName) {
        return studentRepository.findByStudentName(studentName)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentName));
    }

    // Get all students (admin function)
    public List<UserStudent> getAllStudents() {
        return studentRepository.findAll();
    }

    // Update student profile
    public UserStudent updateProfile(String studentName, UserStudent updatedStudent) {
        UserStudent existingStudent = getByName(studentName);
        
        // Update only allowed fields
        existingStudent.setStudentName(updatedStudent.getStudentName());
        if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
            existingStudent.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
        }
        
        return studentRepository.save(existingStudent);
    }

    // Leave a class
    public void leaveClass(String studentName, String joinCode) {
        UserStudent student = getByName(studentName);
        JoinCode code = joinCodeRepository.findByCode(joinCode)
                .orElseThrow(() -> new RuntimeException("Invalid join code: " + joinCode));
        
        StudentClass studentClass = studentClassRepository.findByStudentAndJoinCode(student, code)
                .orElseThrow(() -> new RuntimeException("You are not enrolled in this class"));
        
        // Deactivate instead of deleting (for audit purposes)
        studentClass.setIsActive(false);
        studentClassRepository.save(studentClass);
    }

    // Helper method to convert entity to response
    private StudentClassResponse convertToResponse(StudentClass studentClass) {
        return new StudentClassResponse(
            studentClass.getStudentClassId(),
            studentClass.getStudent().getStudentName(),
            studentClass.getStudent().getStudentId(),
            studentClass.getTeacher().getTeacherName(),
            studentClass.getTeacher().getTeacherId(),
            studentClass.getJoinCode().getSubject(),
            studentClass.getJoinCode().getSectionNo(),
            studentClass.getJoinCode().getCode(),
            studentClass.getJoinCode().getDescription(),
            studentClass.getJoinedAt(),
            studentClass.getIsActive()
        );
    }
}