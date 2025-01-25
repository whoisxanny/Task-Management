package sys.task_manager.dev.services.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.task_manager.dev.models.Comment;
import sys.task_manager.dev.models.Task;
import sys.task_manager.dev.repositories.CommentRepository;
import sys.task_manager.dev.services.CommentService;
import sys.task_manager.dev.services.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TaskService taskService;

    @Transactional
    @Override
    public Comment create(Comment comment, Long taskId) {
        Task task = taskService.getById(taskId);
        comment.setTask(task);
        comment.setLocalDateTime(LocalDateTime.now());
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public List<Comment> getByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

}
