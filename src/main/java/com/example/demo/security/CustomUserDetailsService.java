package com.example.demo.security;

import com.example.demo.model.UserTeacher;
import com.example.demo.model.UserStudent;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CustomUserDetailsService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First try to find as teacher (using teacherName as username)
        try {
            UserTeacher teacher = teacherRepository.findByTeacherName(username)
                    .orElse(null);
            
            if (teacher != null) {
                return User.builder()
                        .username(teacher.getTeacherName())
                        .password(teacher.getPassword())
                        .authorities("ROLE_TEACHER")
                        .build();
            }
        } catch (Exception e) {
            // Continue to check students
        }

        // Then try to find as student (using studentName as username)
        try {
            UserStudent student = studentRepository.findByStudentName(username)
                    .orElse(null);
            
            if (student != null) {
                return User.builder()
                        .username(student.getStudentName())
                        .password(student.getPassword())
                        .authorities("ROLE_STUDENT")
                        .build();
            }
        } catch (Exception e) {
            // User not found
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}