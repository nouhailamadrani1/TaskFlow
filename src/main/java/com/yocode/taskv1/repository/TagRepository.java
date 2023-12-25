package com.yocode.taskv1.repository;


import com.yocode.taskv1.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
