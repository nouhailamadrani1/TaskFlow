package com.yocode.taskv1.repository;


import com.yocode.taskv1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
