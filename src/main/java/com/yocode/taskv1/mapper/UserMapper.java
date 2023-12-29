package com.yocode.taskv1.mapper;

import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface UserMapper {

    @Mapping(target = "assignedTasks", source = "assignedTasks")
    UserDTO entityToDto(User user);

    @Mapping(target = "assignedTasks", source = "assignedTasks")
    User dtoToEntity(UserDTO userDTO);
}
