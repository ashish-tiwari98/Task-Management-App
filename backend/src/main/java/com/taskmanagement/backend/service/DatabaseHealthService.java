package com.taskmanagement.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanagement.backend.repository.DatabaseHealthRepository;

@Service
public class DatabaseHealthService {

    @Autowired
    private DatabaseHealthRepository databaseHealthRepository;

    public void checkDatabase() {
        try {
            Integer result = databaseHealthRepository.checkDatabaseConnection();
            System.out.println("✅ Database is connected. Query Result: " + result);
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
    }
}
