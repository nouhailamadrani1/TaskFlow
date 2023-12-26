package com.yocode.taskv1.mapper;
import com.yocode.taskv1.dto.TaskDTO;
import com.yocode.taskv1.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toTaskDto(Task task);

    Task toTask(TaskDTO taskDto);


}
