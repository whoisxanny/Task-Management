package sys.task_manager.dev.services.Impl;

import sys.task_manager.dev.dtos.JwtRequestDTO;
import sys.task_manager.dev.dtos.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.security.JWTProvider;
import sys.task_manager.dev.services.AuthService;
import sys.task_manager.dev.services.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Override
    public JwtResponseDTO login(JwtRequestDTO loginRequest) {
        JwtResponseDTO jwtResponse = new JwtResponseDTO();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );

        User user = userService.getByEmail(loginRequest.getEmail());
        jwtResponse.setId(user.getId());
        jwtResponse.setEmail(user.getEmail());

        jwtResponse.setAccessToken(jwtProvider.createAccessToken(
                user.getId(), user.getEmail()
        ));
        jwtResponse.setRefreshToken(jwtProvider.createRefreshToken(
                user.getId(), user.getEmail()
        ));

        return jwtResponse;
    }


}
