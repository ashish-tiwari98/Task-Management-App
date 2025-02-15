package com.taskmanagement.backend.controller;

import com.taskmanagement.backend.model.Task;
import com.taskmanagement.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //it marks this class as a MVC REST controller and combines @Controller and @ResponseBody to return json response
@RequestMapping("/tasks") //base url
@CrossOrigin(origins = "http://localhost:5173") //To Allow frontend requests .
public class TaskController {

    @Autowired
    private TaskService taskService;

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
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();// to send HTTP 204 no content response
    }
}
