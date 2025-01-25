package sys.task_manager.dev.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sys.task_manager.dev.enums.TaskPriority;
import sys.task_manager.dev.enums.TaskStatus;

@Data
@Schema(description = "Task DTO")
public class TaskDTO {

    @Hidden
    private Long id;

    @NotNull(message = "Title must be not null.")
    private String title;

    @NotNull(message = "Description must be not null.")
    private String description;

    private TaskPriority taskPriority;

    private TaskStatus taskStatus;

}
