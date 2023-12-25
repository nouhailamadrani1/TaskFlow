package com.yocode.taskv1.service;


import com.yocode.taskv1.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTags();

    TagDTO getTagById(Long tagId);

    TagDTO createTag(TagDTO tagDTO);

    TagDTO updateTag(Long tagId, TagDTO tagDTO);

    void deleteTag(Long tagId);
}
