package com.yocode.taskv1.mapper;

import com.yocode.taskv1.dto.RequestDTO;
import com.yocode.taskv1.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class})
public interface RequestMapper {
    RequestMapper INSTANCE= Mappers.getMapper(RequestMapper.class);

    RequestDTO toRequestDTO(Request request);


    Request toRequest(RequestDTO requestDTO);
}
