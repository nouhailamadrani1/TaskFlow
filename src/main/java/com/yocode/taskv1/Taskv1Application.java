package com.yocode.taskv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.yocode.taskv1")

public class Taskv1Application {

    public static void main(String[] args) {
        SpringApplication.run(Taskv1Application.class, args);
    }

}
