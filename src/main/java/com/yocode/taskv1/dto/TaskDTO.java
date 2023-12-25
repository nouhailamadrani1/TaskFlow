package com.yocode.taskv1.dto;
import com.yocode.taskv1.model.TaskStatus;
import lombok.*;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskStatus status;
    private Long assigneeId;

}
