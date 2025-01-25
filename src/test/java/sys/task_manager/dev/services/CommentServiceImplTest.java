package sys.task_manager.dev.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sys.task_manager.dev.models.Comment;
import sys.task_manager.dev.models.Task;
import sys.task_manager.dev.repositories.CommentRepository;
import sys.task_manager.dev.services.Impl.CommentServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    TaskService taskService;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void create() {

        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        Comment comment = new Comment();
        comment.setText("Sample Comment");

        when(taskService.getById(taskId)).thenReturn(task);

        commentService.create(comment, taskId);

        verify(taskService).getById(taskId);
        verify(commentRepository).save(comment);

        assertEquals(task, comment.getTask());
        assertNotNull(comment.getLocalDateTime());

    }

    @Test
    void getByTaskId() {
        Long taskId = 1L;
        List<Comment> comments = List.of(
                new Comment(1L, "Comment 1", LocalDateTime.now(), null),
                new Comment(2L, "Comment 2", LocalDateTime.now(), null)
        );

        when(commentRepository.findByTaskId(taskId)).thenReturn(comments);

        List<Comment> result = commentService.getByTaskId(taskId);

        verify(commentRepository).findByTaskId(taskId);
        assertEquals(comments.size(), result.size());

        for (int i = 0; i < comments.size(); i++) {
            assertEquals(comments.get(i), result.get(i));
        }
    }

    @Test
    void deleteById() {

        Long commentId = 1L;

        commentService.deleteById(commentId);

        verify(commentRepository).deleteById(commentId);

    }
}