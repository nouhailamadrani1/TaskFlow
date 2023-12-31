package com.yocode.taskv1.service;


import com.yocode.taskv1.dto.TaskDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long taskId) throws ChangeSetPersister.NotFoundException;

    TaskDTO createTask(TaskDTO taskDto, Long currentUserId);

    TaskDTO updateTask(Long taskId, TaskDTO taskDto);

    void deleteTask(Long taskId , Long currentUserId);
    TaskDTO completeTask(Long taskId);
}
