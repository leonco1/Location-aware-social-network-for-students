package mk.ukim.finki.wp.locationawareapp.model.exceptions;

public class NoUserNameFoundException extends RuntimeException{

    public  NoUserNameFoundException()
    {
        super("NoUsernameFound");
    }
}
