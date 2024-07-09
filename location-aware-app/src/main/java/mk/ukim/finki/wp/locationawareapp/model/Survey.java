package mk.ukim.finki.wp.locationawareapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId; // Unique identifier for each response (if needed)
    private String userId; // User identifier
    private String rating; //

    public Survey(String userId, String rating) {
        this.userId=userId;
        this.rating=rating;
    }

    public Survey() {

    }
}
