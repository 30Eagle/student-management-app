package com.example.demo.dto;

import java.time.LocalDateTime;

// JoinClassRequest.java
public class JoinClassRequest {
    private String joinCode;

    public JoinClassRequest() {}

    public JoinClassRequest(String joinCode) {
        this.joinCode = joinCode;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }
}