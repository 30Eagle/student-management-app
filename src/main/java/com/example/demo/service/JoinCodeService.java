package com.example.demo.service;

import com.example.demo.dto.JoinCodeCreateRequest;
import com.example.demo.dto.JoinCodeResponse;
import com.example.demo.model.JoinCode;
import com.example.demo.model.UserTeacher;
import com.example.demo.repository.JoinCodeRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoinCodeService {

    private final JoinCodeRepository joinCodeRepository;
    private final TeacherRepository teacherRepository;

    public JoinCodeService(JoinCodeRepository joinCodeRepository, TeacherRepository teacherRepository) {
        this.joinCodeRepository = joinCodeRepository;
        this.teacherRepository = teacherRepository;
    }

    // Create a new join code
    public JoinCodeResponse createJoinCode(String teacherName, JoinCodeCreateRequest request) {
        // Find teacher by name
        UserTeacher teacher = teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));

        // Check if code already exists
        if (joinCodeRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Join code already exists: " + request.getCode());
        }

        // Create new join code
        JoinCode joinCode = new JoinCode(
                request.getCode(),
                request.getSubject(),
                request.getSectionNo(),
                request.getDescription(),
                teacher
        );

        JoinCode savedJoinCode = joinCodeRepository.save(joinCode);
        return convertToResponse(savedJoinCode);
    }

    // Get all join codes for a teacher
    public List<JoinCodeResponse> getJoinCodesByTeacher(String teacherName) {
        UserTeacher teacher = teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));

        List<JoinCode> joinCodes = joinCodeRepository.findByTeacher(teacher);
        return joinCodes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get active join codes for a teacher
    public List<JoinCodeResponse> getActiveJoinCodesByTeacher(String teacherName) {
        UserTeacher teacher = teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));

        List<JoinCode> joinCodes = joinCodeRepository.findByTeacherAndIsActive(teacher, true);
        return joinCodes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Find join code by code string
    public JoinCode findByCode(String code) {
        return joinCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid join code: " + code));
    }

    // Toggle join code active status
    public JoinCodeResponse toggleJoinCodeStatus(String teacherName, Long joinCodeId) {
        UserTeacher teacher = teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));

        JoinCode joinCode = joinCodeRepository.findById(joinCodeId)
                .orElseThrow(() -> new RuntimeException("Join code not found: " + joinCodeId));

        // Check if the join code belongs to this teacher
        if (!joinCode.getTeacher().getTeacherId().equals(teacher.getTeacherId())) {
            throw new RuntimeException("Unauthorized: Join code does not belong to this teacher");
        }

        // Toggle status
        joinCode.setIsActive(!joinCode.getIsActive());
        JoinCode savedJoinCode = joinCodeRepository.save(joinCode);
        return convertToResponse(savedJoinCode);
    }

    // Update join code
    public JoinCodeResponse updateJoinCode(String teacherName, Long joinCodeId, JoinCodeCreateRequest request) {
        UserTeacher teacher = teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));

        JoinCode joinCode = joinCodeRepository.findById(joinCodeId)
                .orElseThrow(() -> new RuntimeException("Join code not found: " + joinCodeId));

        // Check if the join code belongs to this teacher
        if (!joinCode.getTeacher().getTeacherId().equals(teacher.getTeacherId())) {
            throw new RuntimeException("Unauthorized: Join code does not belong to this teacher");
        }

        // Check if new code already exists (if code is being changed)
        if (!joinCode.getCode().equals(request.getCode()) && 
            joinCodeRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Join code already exists: " + request.getCode());
        }

        // Update fields
        joinCode.setCode(request.getCode());
        joinCode.setSubject(request.getSubject());
        joinCode.setSectionNo(request.getSectionNo());
        joinCode.setDescription(request.getDescription());

        JoinCode savedJoinCode = joinCodeRepository.save(joinCode);
        return convertToResponse(savedJoinCode);
    }

    // Delete join code
    public void deleteJoinCode(String teacherName, Long joinCodeId) {
        UserTeacher teacher = teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherName));

        JoinCode joinCode = joinCodeRepository.findById(joinCodeId)
                .orElseThrow(() -> new RuntimeException("Join code not found: " + joinCodeId));

        // Check if the join code belongs to this teacher
        if (!joinCode.getTeacher().getTeacherId().equals(teacher.getTeacherId())) {
            throw new RuntimeException("Unauthorized: Join code does not belong to this teacher");
        }

        joinCodeRepository.delete(joinCode);
    }

    // Convert entity to response DTO
    private JoinCodeResponse convertToResponse(JoinCode joinCode) {
        return new JoinCodeResponse(
                joinCode.getJoinCodeId(),
                joinCode.getCode(),
                joinCode.getSubject(),
                joinCode.getSectionNo(),
                joinCode.getDescription(),
                joinCode.getIsActive(),
                joinCode.getTeacher().getTeacherId(),
                joinCode.getTeacher().getTeacherName()
        );
    }
}