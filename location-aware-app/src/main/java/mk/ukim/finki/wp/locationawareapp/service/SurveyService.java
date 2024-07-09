package mk.ukim.finki.wp.locationawareapp.service;

import mk.ukim.finki.wp.locationawareapp.model.Survey;

import java.util.List;

public interface SurveyService {
    public Survey createSurvey(String response);
    public List<Survey>getSurveys();
}
