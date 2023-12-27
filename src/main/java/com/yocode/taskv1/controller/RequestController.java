package com.yocode.taskv1.controller;

import com.yocode.taskv1.dto.RequestDTO;
import com.yocode.taskv1.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskflow/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO requestDTO) {
        RequestDTO createdRequest = requestService.createRequest(requestDTO);
        return ResponseEntity.ok(createdRequest);
    }

}
