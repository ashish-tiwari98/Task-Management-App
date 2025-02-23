package com.taskmanagement.backend.service;

import com.taskmanagement.backend.model.Task;
import com.taskmanagement.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

// Service: Contains business logic and handles interactions between the Repository and Controller.
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository; //injects TaskRepository for db operations and recommended is to use constructor injection

    public Task createTask(Task task) {
        return taskRepository.save(task);//method provided by JpaRepository(Spring Data JPA). It returns saved task with generated id and timestamps
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status, Sort.by(Sort.Direction.DESC, "createdAt"));
    }


    public Task updateTask(Long id, Task newTask) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(newTask.getTitle());
        existingTask.setDescription(newTask.getDescription());
        existingTask.setStatus(newTask.getStatus());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);// spring jpa follows specific pattern : deleteByFieldName(), findByFieldName() and many other
    }
}
