package mk.ukim.finki.wp.locationawareapp.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.locationawareapp.model.Enum.MessageType;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String sender;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
