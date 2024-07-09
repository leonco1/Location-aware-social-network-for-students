package mk.ukim.finki.wp.locationawareapp.repository;

import mk.ukim.finki.wp.locationawareapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
