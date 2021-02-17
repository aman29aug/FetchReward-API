package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIdNotFound extends RuntimeException {

    public UserIdNotFound(String message) {
        super(message);
    }
}
