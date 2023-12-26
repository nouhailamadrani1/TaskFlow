package com.yocode.taskv1.service;


import com.yocode.taskv1.dto.TagDTO;
import com.yocode.taskv1.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<TagDTO> getAllTags();

    TagDTO getTagById(Long tagId);

    TagDTO createTag(TagDTO tagDTO);

    TagDTO updateTag(Long tagId, TagDTO tagDTO);

    void deleteTag(Long tagId);
    boolean existsById(Long userId);
    Set<Tag> getTagsByIds(Set<Long> tagIds);
}
