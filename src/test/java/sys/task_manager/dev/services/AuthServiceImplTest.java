package sys.task_manager.dev.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import sys.task_manager.dev.dtos.JwtRequestDTO;
import sys.task_manager.dev.dtos.JwtResponseDTO;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.security.JWTProvider;
import sys.task_manager.dev.services.Impl.AuthServiceImpl;



@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

     @Mock
     UserService userService;
     @Mock
     AuthenticationManager authenticationManager;
     @Mock
     JWTProvider jwtProvider;

     @InjectMocks
     AuthServiceImpl authService;

    @Test
    void login() {

        Long userId = 1L;
        String email = "xanny@gmail.com";
        String password = "password";
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequestDTO request = new JwtRequestDTO();
        request.setEmail(email);
        request.setPassword(password);
        User person = new User();
        person .setId(userId);
        person .setEmail(email);
        Mockito.when(userService.getByEmail(email))
                .thenReturn(person);
        Mockito.when(jwtProvider.createAccessToken(userId, email))
                .thenReturn(accessToken);
        Mockito.when(jwtProvider.createRefreshToken(userId, email))
                .thenReturn(refreshToken);
        JwtResponseDTO response = authService.login(request);
        Mockito.verify(authenticationManager)
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword())
                );
        Assertions.assertEquals(response.getEmail(), email);
        Assertions.assertEquals(response.getId(), userId);
        Assertions.assertNotNull(response.getAccessToken());
        Assertions.assertNotNull(response.getRefreshToken());

    }
}