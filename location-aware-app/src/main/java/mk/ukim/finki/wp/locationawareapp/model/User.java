package mk.ukim.finki.wp.locationawareapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.locationawareapp.model.Enum.Role;

@Setter
@Getter
@Data
@Entity
@Table(name = "chat_user")
public class User {

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public
    User(String username)
    {
        this.username=username;
        this.role=Role.ROLE_USER;
    }

    @Id
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


}
