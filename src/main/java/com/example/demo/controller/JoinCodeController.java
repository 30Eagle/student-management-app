package com.example.demo.controller;

import com.example.demo.dto.JoinCodeCreateRequest;
import com.example.demo.dto.JoinCodeResponse;
import com.example.demo.service.JoinCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/join-codes")
@CrossOrigin(origins = "*")
public class JoinCodeController {

    private final JoinCodeService joinCodeService;

    public JoinCodeController(JoinCodeService joinCodeService) {
        this.joinCodeService = joinCodeService;
    }

    // Create new join code (authenticated teacher only)
    @PostMapping("/create")
    public ResponseEntity<JoinCodeResponse> createJoinCode(
            @RequestBody JoinCodeCreateRequest request, 
            Authentication auth) {
        String teacherName = auth.getName();
        JoinCodeResponse response = joinCodeService.createJoinCode(teacherName, request);
        return ResponseEntity.ok(response);
    }

    // Get all join codes for authenticated teacher
    @GetMapping("/my-codes")
    public ResponseEntity<List<JoinCodeResponse>> getMyJoinCodes(Authentication auth) {
        String teacherName = auth.getName();
        List<JoinCodeResponse> joinCodes = joinCodeService.getJoinCodesByTeacher(teacherName);
        return ResponseEntity.ok(joinCodes);
    }

    // Get active join codes for authenticated teacher
    @GetMapping("/my-active-codes")
    public ResponseEntity<List<JoinCodeResponse>> getMyActiveJoinCodes(Authentication auth) {
        String teacherName = auth.getName();
        List<JoinCodeResponse> joinCodes = joinCodeService.getActiveJoinCodesByTeacher(teacherName);
        return ResponseEntity.ok(joinCodes);
    }

    // Toggle join code active/inactive status
    @PutMapping("/{joinCodeId}/toggle-status")
    public ResponseEntity<JoinCodeResponse> toggleJoinCodeStatus(
            @PathVariable Long joinCodeId, 
            Authentication auth) {
        String teacherName = auth.getName();
        JoinCodeResponse response = joinCodeService.toggleJoinCodeStatus(teacherName, joinCodeId);
        return ResponseEntity.ok(response);
    }

    // Update join code
    @PutMapping("/{joinCodeId}")
    public ResponseEntity<JoinCodeResponse> updateJoinCode(
            @PathVariable Long joinCodeId,
            @RequestBody JoinCodeCreateRequest request,
            Authentication auth) {
        String teacherName = auth.getName();
        JoinCodeResponse response = joinCodeService.updateJoinCode(teacherName, joinCodeId, request);
        return ResponseEntity.ok(response);
    }

    // Delete join code
    @DeleteMapping("/{joinCodeId}")
    public ResponseEntity<Void> deleteJoinCode(
            @PathVariable Long joinCodeId, 
            Authentication auth) {
        String teacherName = auth.getName();
        joinCodeService.deleteJoinCode(teacherName, joinCodeId);
        return ResponseEntity.noContent().build();
    }
}