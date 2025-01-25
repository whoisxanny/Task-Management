package sys.task_manager.dev.controllers;

import sys.task_manager.dev.dtos.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sys.task_manager.dev.mappers.TaskMapper;
import sys.task_manager.dev.models.Task;
import sys.task_manager.dev.security.UserDetailService;
import sys.task_manager.dev.services.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "Task Controller", description = "Handles task management")
public class TaskController {
    private final TaskMapper taskMapper;
    private final TaskService taskService;
    private final UserDetailService userDetailService;

    @GetMapping("/all")
    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks")
    public List<TaskDTO> getAllTask() {
        List<Task> tasks = taskService.getAll();
        return taskMapper.toDto(tasks);
    }

    @GetMapping("/{id}/byAuthor")
    @Operation(summary = "Get tasks by author", description = "Retrieves tasks created by a specific author")
    public List<TaskDTO> getTaskByAuthor(@PathVariable Long id) {
        List<Task> tasks = taskService.getByTaskAuthorId(id);
        return taskMapper.toDto(tasks);
    }

    @GetMapping("/{id}/byAssignee")
    @Operation(summary = "Get tasks by assignee", description = "Retrieves tasks assigned to a specific user")
    public List<TaskDTO> getTaskByAssignee(@PathVariable Long id) {
        List<Task> tasks = taskService.getByTaskAssigneeId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new task", description = "Accepts TaskDTO to create a task")
    public TaskDTO createTask(@RequestBody @Validated final TaskDTO dto) {
        Long personId = userDetailService.getAuthPersonId();
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, personId);
        return taskMapper.toDto(createdTask);
    }

    @PutMapping("/update")
    @Operation(summary = "Update a task", description = "Accepts TaskDTO to update a task")
    public TaskDTO updateTask(@RequestBody @Validated TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @PutMapping("/updateStatus")
    @Operation(summary = "Update task status", description = "Accepts TaskDTO to update a task's status")
    public TaskDTO updateStatus(@RequestBody @Validated TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.changeStatus(task);
        return taskMapper.toDto(updatedTask);
    }

    @PutMapping("/{taskId}/setAssignee")
    @Operation(summary = "Assign users to a task", description = "Assigns a list of users to a specific task")
    public ResponseEntity<?> setAssignees(@PathVariable Long taskId, @RequestBody List<Long> personId) {
        taskService.setAssignee(taskId, personId);
        return ResponseEntity.ok("Task assigned");
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
