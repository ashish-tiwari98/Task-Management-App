package com.taskmanagement.backend.repository;

import com.taskmanagement.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> { //Extends JpaRepository → Gives built-in CRUD operations (no SQL required).
}
