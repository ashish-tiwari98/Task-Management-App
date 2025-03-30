package com.taskmanagement.backend.controller;

import com.taskmanagement.backend.model.Task;
import com.taskmanagement.backend.security.RateLimited;
import com.taskmanagement.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //it marks this class as a MVC REST controller and combines @Controller and @ResponseBody to return json response
@RequestMapping("/tasks") //base url
@CrossOrigin(origins = "http://localhost:5173") //To Allow frontend requests .
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS) // Ensure proxying
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RateLimited(value = 3)
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) { //@RequestBody maps incoming json request body to a task object
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED); // return status and created task in response body
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) { //PathVariable extracts id from URL and assigns to id
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }

    @RateLimited(value = 3)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @RateLimited(value = 3)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();// to send HTTP 204 no content response
    }
}
