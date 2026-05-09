package com.taskmanager.service;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask() {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");

        TaskRequest request = new TaskRequest();
        request.setTitle("TestTitle");
        request.setDescription("TestDescription");

        Task task = new Task();
        task.setId(10L);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(testUser);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(request, testUser);

        assertNotNull(result);
        assertEquals("TestTitle", result.getTitle());
        assertEquals(10L, result.getId());

        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
