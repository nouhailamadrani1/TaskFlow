package com.yocode.taskv1.controller;
import com.yocode.taskv1.dto.TaskDTO;
import com.yocode.taskv1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskflow/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) throws ChangeSetPersister.NotFoundException {
        TaskDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/{currentUserId}")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDto ,@PathVariable Long currentUserId) {
        TaskDTO createdTask = taskService.createTask(taskDto, currentUserId);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDto) {
      return null ;
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{taskId}/complete")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable Long taskId) {
        TaskDTO completedTask = taskService.completeTask(taskId);
        return ResponseEntity.ok(completedTask);
    }
}
