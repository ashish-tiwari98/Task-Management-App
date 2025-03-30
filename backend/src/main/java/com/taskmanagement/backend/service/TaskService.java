package com.taskmanagement.backend.service;

import com.taskmanagement.backend.model.Task;
import com.taskmanagement.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service: Contains business logic and handles interactions between the Repository and Controller.
@Service
public class TaskService {

    private final TaskRepository taskRepository; //injects TaskRepository for db operations and recommended is to use constructor injection as below and can also be done using @Autowired

    // Constructor injection
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);//method provided by JpaRepository(Spring Data JPA). It returns saved task with generated id and timestamps
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }


    public Task updateTask(Long id, Task newTask) {
        Optional<Task> existingTaskOptional = getTaskById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(newTask.getTitle());
            existingTask.setDescription(newTask.getDescription());
            existingTask.setStatus(newTask.getStatus());
            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found with ID: " + id);
        }
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);// spring jpa follows specific pattern : deleteByFieldName(), findByFieldName() and many other
    }
}
