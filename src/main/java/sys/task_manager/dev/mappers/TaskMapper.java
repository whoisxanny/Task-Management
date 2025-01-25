package sys.task_manager.dev.mappers;

import sys.task_manager.dev.dtos.TaskDTO;
import sys.task_manager.dev.models.Task;

@org.mapstruct.Mapper(componentModel = "spring")
public interface TaskMapper extends Mapper<Task, TaskDTO> {
}
