package com.yocode.taskv1.service.serviceImpl;

import com.yocode.taskv1.dto.TaskDTO;
import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.exception.InvalidDateRangeException;
import com.yocode.taskv1.exception.TagNotFoundException;
import com.yocode.taskv1.exception.TaskNotFoundException;
import com.yocode.taskv1.exception.UserNotFoundException;
import com.yocode.taskv1.mapper.TagMapper;
import com.yocode.taskv1.mapper.TaskMapper;
import com.yocode.taskv1.mapper.UserMapper;
import com.yocode.taskv1.model.*;
import com.yocode.taskv1.repository.TagRepository;
import com.yocode.taskv1.repository.TaskRepository;
import com.yocode.taskv1.repository.TaskTagRepository;
import com.yocode.taskv1.service.TaskService;
import com.yocode.taskv1.service.UserService;
import com.yocode.taskv1.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    private final UserMapper userMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    private  final TaskTagRepository taskTagRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, UserService userService, TagService tagService,
                           UserMapper userMapper, TagRepository tagRepository ,TagMapper tagMapper, TaskTagRepository taskTagRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.tagService = tagService;
        this.userMapper = userMapper;
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.taskTagRepository = taskTagRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::toTaskDto).collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDto, Long currentUserId) {
        validateAssigneeAndTagsExistence(taskDto);

        boolean isManager = userService.isUserManager(currentUserId);

        Task task = taskMapper.toTask(taskDto);

        if (taskDto.getStartDate() != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate allowedStartDate = currentDate.plusDays(3);

            if (taskDto.getStartDate().isAfter(allowedStartDate)) {
                throw new InvalidDateRangeException("The start date is between 3 days from the current day");
            }
        }
        if (taskDto.getStartDate() != null && taskDto.getEndDate() != null
                && taskDto.getEndDate().isBefore(taskDto.getStartDate())) {
            throw new InvalidDateRangeException("End date must be after start date");
        }

        if (taskDto.getAssigneeId() != null) {

            if (isManager || currentUserId.equals(taskDto.getAssigneeId())) {
                UserDTO user = userService.getUserById(taskDto.getAssigneeId());
                task.setAssignee(userMapper.dtoToEntity(user));
            } else {
                throw new InvalidDateRangeException("You are not authorized to assign tasks to other users");
            }
        }

        // createdBy
        UserDTO createdBy = userService.getUserById(currentUserId);
        task.setCreatedBy(userMapper.dtoToEntity(createdBy));

        Task savedTask = taskRepository.save(task);

        if (taskDto.getTagIds() != null && !taskDto.getTagIds().isEmpty()) {
            Set<Long> tagIds = taskDto.getTagIds();
            Set<Tag> existingTags = tagRepository.findAllById(tagIds)
                    .stream()
                    .collect(Collectors.toSet());

            Set<Long> nonExistingTagIds = tagIds.stream()
                    .filter(tagId -> existingTags.stream().noneMatch(tag -> tag.getId().equals(tagId)))
                    .collect(Collectors.toSet());

            if (!nonExistingTagIds.isEmpty()) {
                throw new TagNotFoundException("Tags not found with IDs: " + nonExistingTagIds);
            }

            Set<TaskTag> taskTags = existingTags.stream()
                    .map(tag -> TaskTag.builder().tag(tag).task(savedTask).build())
                    .collect(Collectors.toSet());

            taskTagRepository.saveAll(taskTags);
        }

        return taskMapper.toTaskDto(savedTask);
    }




    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDto) {
        return null;
    }

    @Override
    public void deleteTask(Long taskId, Long currentUserId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        UserDTO loggedInUser = userService.getUserById(currentUserId);
        if (currentUserId  == task.getCreatedBy().getId()) {
            taskRepository.deleteById(taskId);
            System.out.println("deleted");
        } else if (loggedInUser.getRole() == Role.MANAGER) {
            taskRepository.deleteById(taskId);
            System.out.println("deleted");
        } else {
            throw new UserNotFoundException("You do not have permission to delete this task.");
        }
    }
    @Override
    public TaskDTO completeTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new TaskNotFoundException("Task is already marked as completed");
        }

        LocalDate currentDate = LocalDate.now();
        if (task.getEndDate() != null && currentDate.isAfter(task.getEndDate())) {
            throw new TaskNotFoundException("Task cannot be completed after the deadline");
        }

        task.setStatus(TaskStatus.COMPLETED);
        Task savedTask = taskRepository.save(task);

        return taskMapper.toTaskDto(savedTask);
    }

    private void validateAssigneeAndTagsExistence(TaskDTO taskDto) {
        if (taskDto.getAssigneeId() != null && !userService.existsById(taskDto.getAssigneeId())) {
            throw new UserNotFoundException("User not found with id: " + taskDto.getAssigneeId());
        }

        if (taskDto.getTagIds() != null) {
            for (Long tagId : taskDto.getTagIds()) {
                if (!tagService.existsById(tagId)) {
                    throw new TagNotFoundException("Tag not found with id: " + tagId);
                }
            }
        }
    }
}