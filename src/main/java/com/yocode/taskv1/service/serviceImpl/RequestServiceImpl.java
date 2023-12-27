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
import java.time.YearMonth;

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
        Long createdById = requestDTO.getCreatedById();
        Long oldTaskId = requestDTO.getOldTaskId();
        RequestType requestType = requestDTO.getRequestType();
        RequestStatus requestStatus = requestDTO.getRequestStatus();

        UserDTO createdBy = userService.getUserById(createdById);
        Task oldTask = taskRepository.findById(oldTaskId)
                .orElseThrow(() -> new InvalidDateRangeException("Task not found with id: " + oldTaskId));


        validateTokenUsage(createdById, requestType);

        requestDTO.setCreatedById(createdById);
        requestDTO.setOldTaskId(oldTaskId);
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

    private void validateTokenUsage(Long createdById, RequestType requestType) {

        long countDay = requestRepository.countBycreatedByIdAndRequestDateAndRequestType(createdById, LocalDate.now(), RequestType.TASK_REPLACEMENT);

        YearMonth yearMonth = YearMonth.now();
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();


        Long monthlyDeletion = requestRepository.countBycreatedByIdAndRequestDateBetweenAndRequestType(
                createdById, startOfMonth, endOfMonth, RequestType.TASK_DELETED
        );

        if (RequestType.TASK_REPLACEMENT.equals(requestType)) {

            if (countDay >= 2) {
                throw new InvalidDateRangeException("You dont have replacement tokens for today");
            }
        } else if (RequestType.TASK_DELETED.equals(requestType)) {

            if (monthlyDeletion >= 1) {
                throw new InvalidDateRangeException("You dont have deletion tokens for this month");
            }
        }
    }
}
