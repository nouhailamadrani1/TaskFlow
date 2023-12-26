package com.yocode.taskv1.mapper;
import com.yocode.taskv1.dto.TagDTO;
import com.yocode.taskv1.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toTagDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}