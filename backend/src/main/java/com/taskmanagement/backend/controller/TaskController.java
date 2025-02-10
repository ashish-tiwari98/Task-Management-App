package com.taskmanagement.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {
    @GetMapping("/health")
    public String checkHealth() {
        return "Backend is running!";
    }
}
