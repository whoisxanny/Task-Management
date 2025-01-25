package sys.task_manager.dev.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDTO {

    @Hidden
    private Long id;

    @Schema(description = "User name", example = "John Doe")
    @NotNull(message = "Name must be not null.")
    private String name;

    @Schema(description = "User email", example = "aleks123@gmail.com")
    @NotNull(message = "Email must be not null.")
    @Email
    private String email;

    @Schema(description = "User encrypted password")
    @NotNull(message = "Password must be not null.")
    private String password;

}
