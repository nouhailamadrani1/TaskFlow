package com.yocode.taskv1.dto;

import com.yocode.taskv1.model.RequestStatus;
import com.yocode.taskv1.model.RequestType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestDTO {
    private Long id;
    private Long newAssigneeId;
    private Long createdById;
    private Long oldTaskId;
    private Long newTaskId;
    private RequestStatus requestStatus;
    private RequestType requestType;
    private LocalDate requestDate;

}
