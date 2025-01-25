package sys.task_manager.dev.services;



import sys.task_manager.dev.models.Task;

import java.util.List;

public interface TaskService {

    Task create(Task task, Long id);
    Task getById(Long id);

    Task update(Task task);

    void deleteById(Long taskId);

    List<Task> getAll();

    List<Task> getByTaskAuthorId(Long authorId);

    List<Task> getByTaskAssigneeId(Long assigneeId);
    Task changeStatus(Task task);
    void setAssignee(Long taskId, List<Long> personId);
}
