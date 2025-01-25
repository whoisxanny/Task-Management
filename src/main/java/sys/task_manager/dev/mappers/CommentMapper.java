package sys.task_manager.dev.mappers;

import sys.task_manager.dev.dtos.CommentDTO;
import sys.task_manager.dev.models.Comment;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CommentMapper extends Mapper<Comment, CommentDTO> {

}
