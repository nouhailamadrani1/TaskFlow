package com.yocode.taskv1.service.serviceImpl;
import com.yocode.taskv1.dto.TaskDTO;
import com.yocode.taskv1.mapper.TaskMapper;
import com.yocode.taskv1.model.Task;
import com.yocode.taskv1.repository.TaskRepository;
import com.yocode.taskv1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::toTaskDto).collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(Long taskId)  {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + taskId));
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDto) {
        Task task = taskMapper.toTask(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskDto(savedTask);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDto) {

        return null;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
