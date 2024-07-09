package mk.ukim.finki.wp.locationawareapp.repository;

import mk.ukim.finki.wp.locationawareapp.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {
}
