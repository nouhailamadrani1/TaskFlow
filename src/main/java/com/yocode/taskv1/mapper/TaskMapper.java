package com.yocode.taskv1.mapper;

import com.yocode.taskv1.dto.TaskDTO;
import com.yocode.taskv1.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE= Mappers.getMapper(TaskMapper.class);
    TaskDTO toTaskDto(Task task);
    Task toTask(TaskDTO taskDto);
}
