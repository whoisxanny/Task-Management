package sys.task_manager.dev.controllers;

import sys.task_manager.dev.dtos.JwtRequestDTO;
import sys.task_manager.dev.dtos.JwtResponseDTO;
import sys.task_manager.dev.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sys.task_manager.dev.mappers.UserMapper;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.services.AuthService;
import sys.task_manager.dev.services.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Handles user authentication and registration")
public class AuthController {
    private final UserMapper personMapper;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Accepts a UserDTO to create a new user")
    public UserDTO register(@RequestBody @Validated final UserDTO userDTO) {
        User user = personMapper.toEntity(userDTO);
        User createdUser = userService.create(user);
        return personMapper.toDto(createdUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Accepts JwtRequestDTO to authenticate a user")
    public JwtResponseDTO login(@RequestBody @Validated JwtRequestDTO requestDTO) {
        return authService.login(requestDTO);
    }
}