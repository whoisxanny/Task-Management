package sys.task_manager.dev.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
public class ExceptionFull {
    private String message;
    private Map<String,String> errors;

    public ExceptionFull(String message) {
        this.message = message;
    }
}
