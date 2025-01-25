package sys.task_manager.dev.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sys.task_manager.dev.enums.UserRoles;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.repositories.UserRepository;
import sys.task_manager.dev.services.Impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    void getByEmail() {

        String email = "test@example.com";
        User person = new User();
        person.setEmail(email);

        when(userRepository.findPersonByEmail(email)).thenReturn(Optional.of(person));

        User result = userServiceImpl.getByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());

    }

    @Test
    void getById() {

        Long id = 1L;
        User person = new User();
        person.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(person));

        User result = userServiceImpl.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

    }

    @Test
    void create() {

        User person = new User();
        person.setEmail("test@example.com");
        person.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userServiceImpl.create(person);

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(person);

        assertEquals("encodedPassword", person.getPassword());
        assertEquals(UserRoles.ROLE_USER, person.getRole());

    }
}