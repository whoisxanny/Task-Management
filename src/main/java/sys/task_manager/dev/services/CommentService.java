package sys.task_manager.dev.services;


import sys.task_manager.dev.models.Comment;

import java.util.List;

public interface CommentService {

    Comment create(Comment comment, Long taskId);

    List<Comment> getByTaskId(Long taskId);

    void deleteById(Long id);

}
