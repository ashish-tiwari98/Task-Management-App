package com.taskmanagement.backend.controller;

import com.taskmanagement.backend.dto.AuthResponse;
import com.taskmanagement.backend.dto.LoginRequest;
import com.taskmanagement.backend.model.User;
import com.taskmanagement.backend.repository.UserRepository;
import com.taskmanagement.backend.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if(user.isEmpty() || !user.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtUtil.generateToken(user.get().getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
