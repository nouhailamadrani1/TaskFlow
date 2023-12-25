package com.yocode.taskv1.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private Set<Task> assignedTasks;
}
