package com.ustp.quizhub.controller;

import com.ustp.quizhub.model.User;
import com.ustp.quizhub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            User user = authService.registerUser(
                request.studentId(),
                request.password(),
                request.fullName(),
                request.email()
            );
            return ResponseEntity.ok(new RegisterResponse(user.getStudentId(), user.getFullName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            User user = authService.authenticateUser(request.studentId(), request.password());
            return ResponseEntity.ok(new LoginResponse(user.getStudentId(), user.getFullName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

record RegisterRequest(String studentId, String password, String fullName, String email) {}
record RegisterResponse(String studentId, String fullName) {}
record LoginRequest(String studentId, String password) {}
record LoginResponse(String studentId, String fullName) {} 