package com.yocode.taskv1.mapper;
import com.yocode.taskv1.dto.RequestDTO;
import com.yocode.taskv1.model.Request;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestDTO toRequestDTO(Request request);
    Request toRequest(RequestDTO requestDTO);
}