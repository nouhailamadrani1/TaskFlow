package com.yocode.taskv1.mapper;

import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    UserDTO entityToDto(User user);

    User dtoToEntity(UserDTO userDTO);
}