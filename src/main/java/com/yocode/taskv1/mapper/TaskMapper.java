package com.yocode.taskv1.mapper;


import com.yocode.taskv1.dto.TaskDTO;
import com.yocode.taskv1.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TaskMapper {

    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskDTO toTaskDto(Task task);

    Task toTask(TaskDTO taskDto);
}
