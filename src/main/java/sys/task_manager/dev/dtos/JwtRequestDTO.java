package sys.task_manager.dev.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequestDTO {

    @Schema(description = "email", example = "aleks123@gmail.com")
    @NotNull(message = "Email must be not null.")
    private String email;

    @Schema(description = "password", example = "123")
    @NotNull(message = "Password must be not null.")
    private String password;

}
