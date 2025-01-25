package sys.task_manager.dev.services;

import sys.task_manager.dev.dtos.JwtRequestDTO;
import sys.task_manager.dev.dtos.JwtResponseDTO;

public interface AuthService {

    JwtResponseDTO login(JwtRequestDTO loginRequest);

}
