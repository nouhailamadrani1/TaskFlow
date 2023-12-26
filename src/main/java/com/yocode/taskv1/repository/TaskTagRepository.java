package com.yocode.taskv1.repository;

import com.yocode.taskv1.model.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long>{
}
