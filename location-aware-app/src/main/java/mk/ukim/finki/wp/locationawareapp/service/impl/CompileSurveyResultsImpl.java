package mk.ukim.finki.wp.locationawareapp.service.impl;

import mk.ukim.finki.wp.locationawareapp.model.Survey;
import mk.ukim.finki.wp.locationawareapp.repository.SurveyRepository;
import mk.ukim.finki.wp.locationawareapp.service.CompileSurveyResults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CompileSurveyResultsImpl implements CompileSurveyResults {
    private final SurveyRepository surveyRepository;

    public CompileSurveyResultsImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Map<String, Long> CompileSurveys() {
        List<Survey>surveys=surveyRepository.findAll();
        HashMap<String,Long>surveyMap=new HashMap<>();
        for(Survey sur:surveys)
        {
         surveyMap.putIfAbsent(sur.getUserId()  ,0L);
         surveyMap.computeIfPresent(sur.getUserId(),(i,j)->{
             j++;
             return j;
         });
        }

        return surveyMap;
    }


}
