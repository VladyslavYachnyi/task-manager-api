package com.taskmanager.service;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task createTask(TaskRequest request, User currentUser) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(currentUser);

        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(User currentUser) {
        return taskRepository.findByUserId(currentUser.getId());
    }

    public Task updateTask(Long taskId, TaskRequest request, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task is not found"));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have permission to update this task");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task is not found"));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have permission to delete this task");
        }

        taskRepository.delete(task);
    }

    public Task toggleTaskCompletion(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task is not found"));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have permission to toggle this task");
        }

        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }
}
