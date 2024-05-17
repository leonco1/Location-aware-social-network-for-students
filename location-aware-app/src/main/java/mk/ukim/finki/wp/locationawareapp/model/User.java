package mk.ukim.finki.wp.locationawareapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chat_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public User(String username) {
        this.username = username;
    }
}
