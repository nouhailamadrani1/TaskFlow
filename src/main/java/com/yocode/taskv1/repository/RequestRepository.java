package com.yocode.taskv1.repository;

import com.yocode.taskv1.model.Request;
import com.yocode.taskv1.model.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RequestRepository extends JpaRepository<Request, Long> {
    long countBycreatedByIdAndRequestDateAndRequestType(Long userId, LocalDate requestDate, RequestType requestType);
    long countBycreatedByIdAndRequestDateBetweenAndRequestType(
            Long userId,
            LocalDate startOfMonth,
            LocalDate endOfMonth,
            RequestType requestType
    );
}
