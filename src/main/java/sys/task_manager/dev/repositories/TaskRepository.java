package sys.task_manager.dev.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sys.task_manager.dev.models.Task;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> getTaskByTaskAuthorId(Long authorId);

    List<Task> getTaskByAssigneesId(Long assigneesId);

}
