package sys.task_manager.dev.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sys.task_manager.dev.models.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByTaskId(Long id);
}
