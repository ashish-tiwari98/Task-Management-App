package com.taskmanagement.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity // Maps class to a database table.
@Getter //Reduce boilerplate code (lombok annotation)
@Setter //Reduce boilerplate code (lombok annotation)
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generates unique IDs automatically.
    private Long id;

    private String title;
    private String description;
    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
