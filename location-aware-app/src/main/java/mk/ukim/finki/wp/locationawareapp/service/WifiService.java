package mk.ukim.finki.wp.locationawareapp.service;

import java.io.IOException;
import java.util.Optional;

public interface WifiService {

    Optional<String> getIpAddress() throws IOException;
    void SendMessage() throws IOException;
    Optional<String>getSubnetMask() throws IOException;
}
