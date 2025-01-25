package sys.task_manager.dev.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request after login")
public class JwtResponseDTO {

    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;

}
