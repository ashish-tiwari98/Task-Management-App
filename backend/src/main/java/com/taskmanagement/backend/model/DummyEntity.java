package com.taskmanagement.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dummy_table")  // Ensure this table exists in your DB
public class DummyEntity {
    @Id
    private Long id;
}
