package com.taskmanagement.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/*
Entity vs Model:

Entity: Represents a database table and is managed by JPA (@Entity).
Model: General class that holds data but is not tied to a database. Used for business logic.
 */
@Entity // Marks this class as a JPA entity (maps to db table)
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //id is primary key and auto incremented
    private Long id;
    @Column(unique = true) //this ensures it must be unique
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
