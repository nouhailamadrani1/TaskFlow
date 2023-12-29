package com.yocode.taskv1.mapper;
import com.yocode.taskv1.dto.TagDTO;
import com.yocode.taskv1.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE= Mappers.getMapper(TagMapper.class);
    TagDTO toTagDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}