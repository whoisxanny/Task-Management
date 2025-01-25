package sys.task_manager.dev.services.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.task_manager.dev.enums.TaskStatus;
import sys.task_manager.dev.exceptions.ResourceNotFoundException;
import sys.task_manager.dev.models.Task;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.repositories.TaskRepository;
import sys.task_manager.dev.services.TaskService;
import sys.task_manager.dev.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Transactional
    @Override
    public Task create(Task task, Long id) {
        task.setTaskStatus(TaskStatus.TO_DO);
        User user = userService.getById(id);
        task.setTaskAuthor(user);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Id is null"));
    }

    @Transactional
    @Override
    public Task update(Task task) {
        Task newTask = getById(task.getId());
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setTaskPriority(task.getTaskPriority());
        taskRepository.save(newTask);
        return newTask;
    }

    @Transactional
    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> getAll() {
      return taskRepository.findAll();
    }

    @Override
    public List<Task> getByTaskAuthorId(Long authorId) {
        Optional.ofNullable(authorId)
                .orElseThrow(()-> new ResourceNotFoundException("Id cannot be null"));

        List<Task> tasks = taskRepository.getTaskByTaskAuthorId(authorId);

        Optional.of(tasks)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("author dont have tasks"));

        return tasks;
    }

    @Override
    public List<Task> getByTaskAssigneeId(Long assigneeId) {
        Optional.ofNullable(assigneeId)
                .orElseThrow(()-> new ResourceNotFoundException("Id cannot be null"));

        List<Task> tasks = taskRepository.getTaskByAssigneesId(assigneeId);

        Optional.of(tasks)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("Assignee dont have tasks"));

        return tasks;
    }

    @Transactional
    @Override
    public Task changeStatus(Task task) {
        Task newTask = getById(task.getId());
        newTask.setTaskStatus(task.getTaskStatus());
        taskRepository.save(newTask);
        return newTask;
    }

    @Transactional
    @Override
    public void setAssignee(Long taskId, List<Long> personId) {
        Task taskById = getById(taskId);
        personId.forEach(Id -> {
            User userById = userService.getById(Id);
            taskById.getAssignees().add(userById);
        });

        taskRepository.save(taskById);
    }
}
