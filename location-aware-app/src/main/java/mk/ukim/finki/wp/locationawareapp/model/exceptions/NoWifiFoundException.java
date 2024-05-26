package mk.ukim.finki.wp.locationawareapp.model.exceptions;

public class NoWifiFoundException extends RuntimeException{

    public NoWifiFoundException()
    {
        super("No wifi found");
    }
}
