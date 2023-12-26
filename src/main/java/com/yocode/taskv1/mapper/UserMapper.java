package com.yocode.taskv1.mapper;
import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDto(User user);

    User dtoToEntity(UserDTO userDTO);
}
