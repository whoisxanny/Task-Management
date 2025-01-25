package sys.task_manager.dev.services;

import sys.task_manager.dev.models.User;

public interface UserService {

     User getByEmail(String email);

     User getById(Long id);

     User create(User user);

}
