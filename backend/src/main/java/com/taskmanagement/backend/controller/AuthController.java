package com.taskmanagement.backend.controller;

import com.taskmanagement.backend.dto.AuthResponse;
import com.taskmanagement.backend.dto.LoginRequest;
import com.taskmanagement.backend.dto.RegisterRequest;
import com.taskmanagement.backend.model.User;
import com.taskmanagement.backend.repository.UserRepository;
import com.taskmanagement.backend.security.JwtUtil;
import com.taskmanagement.backend.security.RateLimited;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173") //To Allow frontend requests .
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if(user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtUtil.generateToken(user.get().getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }
}
