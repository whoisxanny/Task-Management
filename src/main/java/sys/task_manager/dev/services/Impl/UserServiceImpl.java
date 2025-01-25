package sys.task_manager.dev.services.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.task_manager.dev.enums.UserRoles;
import sys.task_manager.dev.exceptions.ResourceNotFoundException;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.repositories.UserRepository;
import sys.task_manager.dev.services.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getByEmail(String email) {
        return userRepository.findPersonByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found."));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRoles.ROLE_USER);
        userRepository.save(user);
        return user;
    }

}
