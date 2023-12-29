package com.yocode.taskv1.mapper;

import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);
    //@Mapping(target = "assignedTasks", source = "assignedTasks")
    UserDTO entityToDto(User user);

   // @Mapping(target = "assignedTasks", source = "assignedTasks")
    User dtoToEntity(UserDTO userDTO);
}
