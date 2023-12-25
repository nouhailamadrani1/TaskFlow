package com.yocode.taskv1.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TagDTO {
    private Long id;
    private String name;
}