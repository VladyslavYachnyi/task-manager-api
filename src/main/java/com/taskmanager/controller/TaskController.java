package com.taskmanager.controller;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request, @AuthenticationPrincipal User currentUser) {
        Task createdTask = taskService.createTask(request,currentUser);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getUserTasks(@AuthenticationPrincipal User currentUser) {
        List<Task> tasks = taskService.getUserTasks(currentUser);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal User currentUser) {
        try {
            Task updatedTask = taskService.updateTask(id, request, currentUser);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {

        try {
            taskService.deleteTask(id, currentUser);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> toggleTaskCompletion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {

        try {
            Task updatedTask = taskService.toggleTaskCompletion(id, currentUser);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
