package com.yocode.taskv1.repository;

import com.yocode.taskv1.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
