package mk.ukim.finki.wp.locationawareapp.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.locationawareapp.model.Enum.MessageType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}
