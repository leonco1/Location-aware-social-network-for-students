package mk.ukim.finki.wp.locationawareapp.service.impl;

import mk.ukim.finki.wp.locationawareapp.model.Survey;
import mk.ukim.finki.wp.locationawareapp.repository.SurveyRepository;
import mk.ukim.finki.wp.locationawareapp.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyServiceImpl implements SurveyService {
    private SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey createSurvey(String response) {
        String[]parts=response.split(":");
        Survey survey=new Survey(parts[0],parts[1]);
        surveyRepository.save(survey);
        return survey;
    }

    @Override
    public List<Survey> getSurveys() {
        return surveyRepository.findAll();
    }


}
