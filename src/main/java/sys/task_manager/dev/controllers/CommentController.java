package sys.task_manager.dev.controllers;

import sys.task_manager.dev.dtos.CommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sys.task_manager.dev.mappers.CommentMapper;
import sys.task_manager.dev.models.Comment;
import sys.task_manager.dev.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "Comment Controller", description = "Handles comment management")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/{taskId}/create")
    @Operation(summary = "Create a comment", description = "Accepts CommentDTO and taskId to create a comment")
    public CommentDTO create(@RequestBody @Validated CommentDTO commentDTO, @PathVariable Long taskId) {
        Comment comment = commentMapper.toEntity(commentDTO);
        Comment createdComment = commentService.create(comment, taskId);
        return commentMapper.toDto(createdComment);
    }

    @GetMapping("/{taskId}/getByTask")
    @Operation(summary = "Get comments by task ID", description = "Retrieves a list of comments for a given task")
    public List<CommentDTO> getByTaskId(@PathVariable Long taskId) {
        List<Comment> list = commentService.getByTaskId(taskId);
        return commentMapper.toDto(list);
    }

    @DeleteMapping("/{commentId}/delete")
    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.ok("Comment deleted");
    }
}