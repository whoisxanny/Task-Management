package sys.task_manager.dev.mappers;

import sys.task_manager.dev.dtos.UserDTO;
import sys.task_manager.dev.models.User;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper extends Mapper<User, UserDTO> {
}
