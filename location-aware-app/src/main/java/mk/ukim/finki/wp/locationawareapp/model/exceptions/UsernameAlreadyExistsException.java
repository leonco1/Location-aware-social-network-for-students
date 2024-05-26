package mk.ukim.finki.wp.locationawareapp.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException()
    {
        super("Username already exists");
    }
}
