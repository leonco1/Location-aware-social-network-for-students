package mk.ukim.finki.wp.locationawareapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WifiService {

    List<String> getIpAddress() throws IOException;
}
