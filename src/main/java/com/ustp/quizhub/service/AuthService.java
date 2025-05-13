package com.ustp.quizhub.service;

import com.ustp.quizhub.model.User;
import com.ustp.quizhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(String studentId, String password, String fullName, String email) {
        if (userRepository.existsByStudentId(studentId)) {
            throw new RuntimeException("Student ID already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setStudentId(studentId);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setEmail(email);
        
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_STUDENT");
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User authenticateUser(String studentId, String password) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
} 