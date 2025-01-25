package sys.task_manager.dev.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sys.task_manager.dev.enums.TaskPriority;
import sys.task_manager.dev.enums.TaskStatus;
import sys.task_manager.dev.exceptions.ResourceNotFoundException;
import sys.task_manager.dev.models.Task;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.repositories.TaskRepository;
import sys.task_manager.dev.services.Impl.TaskServiceImpl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;
    @Mock
    UserService userService;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void create() {

        Task task = new Task();
        task.setTitle("Sample Task");

        Long authorId = 101L;
        User author = new User();
        author.setId(authorId);

        when(userService.getById(authorId)).thenReturn(author);

        Task result = taskService.create(task, authorId);

        verify(userService).getById(authorId);
        verify(taskRepository).save(task);

        assertEquals(TaskStatus.TO_DO, task.getTaskStatus());
        assertEquals(author, task.getTaskAuthor());
        assertEquals(task, result);

    }

    @Test
    void getById() {

      Long id = 1L;
      Task task = new Task();
      task.setId(id);

      when(taskRepository.findById(id)).thenReturn(Optional.of(task));
      Task testTask = taskService.getById(id);
      verify(taskRepository).findById(id);

      Assertions.assertEquals(task,testTask);

    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        when(taskRepository.findById(id))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> taskService.getById(id));
        verify(taskRepository).findById(id);
    }

    @Test
    void update() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setTaskPriority(TaskPriority.LOW);

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("New Title");
        updatedTask.setDescription("New Description");
        updatedTask.setTaskPriority(TaskPriority.HIGH);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        Task result = taskService.update(updatedTask);

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(existingTask);

        assertEquals("New Title", existingTask.getTitle());
        assertEquals("New Description", existingTask.getDescription());
        assertEquals(TaskPriority.HIGH, existingTask.getTaskPriority());
        assertEquals(existingTask, result);

    }

    @Test
    void deleteById() {

        Long id = 1L;
        taskService.deleteById(id);
        verify(taskRepository).deleteById(id);

    }

    @Test
    void getAll() {

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAll();

        assertEquals(tasks.size(), result.size(), "Returned list size should match");
        assertEquals(tasks.get(0).getTitle(), result.get(0).getTitle(), "Task titles should match");
        assertEquals(tasks.get(1).getTitle(), result.get(1).getTitle(), "Task titles should match");

    }

    @Test
    void getByTaskAuthorId() {

        Long userId = 1L;

        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }

        when(taskRepository.getTaskByTaskAuthorId(userId))
                .thenReturn(tasks);

        List<Task> testTasks = taskService.getByTaskAuthorId(userId);
        verify(taskRepository).getTaskByTaskAuthorId(userId);
        Assertions.assertEquals(tasks, testTasks);

    }

    @Test
    void getByTaskAssigneeId() {

        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }

        when(taskRepository.getTaskByAssigneesId(userId))
                .thenReturn(tasks);

        List<Task> testTasks = taskService.getByTaskAssigneeId(userId);
        verify(taskRepository).getTaskByAssigneesId(userId);
        Assertions.assertEquals(tasks, testTasks);

    }

    @Test
    void changeStatus() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTaskStatus(TaskStatus.TO_DO);

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTaskStatus(TaskStatus.DONE);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        Task result = taskService.changeStatus(updatedTask);

        Mockito.verify(taskRepository).findById(1L);
        Mockito.verify(taskRepository).save(existingTask);

        assertEquals(TaskStatus.DONE, existingTask.getTaskStatus());
        assertEquals(updatedTask, result);

    }

    @Test
    void setAssignee() {

        Long taskId = 1L;
        List<Long> personIds = List.of(101L, 102L);

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setAssignees(new HashSet<>());

        User person1 = new User();
        person1.setId(101L);

        User person2 = new User();
        person2.setId(102L);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(userService.getById(101L)).thenReturn(person1);
        when(userService.getById(102L)).thenReturn(person2);

        taskService.setAssignee(taskId, personIds);

        verify(taskRepository).findById(taskId);
        verify(userService,Mockito.times(2)).getById(anyLong());
        verify(taskRepository).save(existingTask);

        assertTrue(existingTask.getAssignees().contains(person1));
        assertTrue(existingTask.getAssignees().contains(person2));

    }
}