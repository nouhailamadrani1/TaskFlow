package com.yocode.taskv1.dto;


import com.yocode.taskv1.model.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private Role role;
    private Set<TaskDTO> assignedTasks;
}

