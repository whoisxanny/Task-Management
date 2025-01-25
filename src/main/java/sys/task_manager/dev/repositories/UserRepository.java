package sys.task_manager.dev.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sys.task_manager.dev.models.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findPersonByEmail(String email);
   Boolean existsPersonByEmail(String email);
}
