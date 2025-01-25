package sys.task_manager.dev.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sys.task_manager.dev.enums.UserRoles;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToMany(mappedBy = "assignees")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "taskAuthor")
    private List<Task> authoredTasks;
}
