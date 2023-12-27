package com.yocode.taskv1.service.serviceImpl;

import com.yocode.taskv1.dto.RequestDTO;
import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.exception.InvalidDateRangeException;
import com.yocode.taskv1.mapper.RequestMapper;
import com.yocode.taskv1.mapper.UserMapper;
import com.yocode.taskv1.model.*;
import com.yocode.taskv1.repository.RequestRepository;
import com.yocode.taskv1.repository.TaskRepository;
import com.yocode.taskv1.service.RequestService;
import com.yocode.taskv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final TaskRepository taskRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper, UserService userService, TaskRepository taskRepository ,UserMapper userMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.userService = userService;
        this.taskRepository = taskRepository;
        this.userMapper = userMapper;
    }


    @Override
    public RequestDTO createRequest(RequestDTO requestDTO) {
        Long newAssigneeId = requestDTO.getNewAssigneeId();
        Long createdById = requestDTO.getCreatedById();
        Long oldTaskId = requestDTO.getOldTaskId();
        Long newTaskId = requestDTO.getNewTaskId();
        RequestType requestType =requestDTO.getRequestType();
        RequestStatus requestStatus =requestDTO.getRequestStatus();

        UserDTO createdBy = userService.getUserById(createdById) ;

        Task oldTask = taskRepository.findById(oldTaskId)
                .orElseThrow(() -> new InvalidDateRangeException("Task not found with id: " + oldTaskId));


        requestDTO.setNewAssigneeId(newAssigneeId);
        requestDTO.setCreatedById(createdById);
        requestDTO.setOldTaskId(oldTaskId);
        requestDTO.setNewTaskId(newTaskId);
        requestDTO.setRequestStatus(requestStatus);
        requestDTO.setRequestType(requestType);
        requestDTO.setRequestDate(LocalDate.now());

        Request request = requestMapper.toRequest(requestDTO);


        request.setNewAssignee(null);
        request.setCreatedBy(userMapper.dtoToEntity(createdBy));
        request.setOldTask(oldTask);
        request.setNewTask(null);
        request.setRequestDate(LocalDate.now());
        request.setRequestStatus(requestStatus);
        request.setRequestType(requestType);

        Request savedRequest = requestRepository.save(request);
        return requestMapper.toRequestDTO(savedRequest);
    }
}
