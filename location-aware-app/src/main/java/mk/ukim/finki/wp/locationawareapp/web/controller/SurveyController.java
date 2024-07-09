package mk.ukim.finki.wp.locationawareapp.web.controller;

import mk.ukim.finki.wp.locationawareapp.service.CompileSurveyResults;
import mk.ukim.finki.wp.locationawareapp.service.SurveyService;
import mk.ukim.finki.wp.locationawareapp.service.UserSessionRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

@Controller
@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;
    private CompileSurveyResults compileSurveyResults;
    private UserSessionRegistry userSessionRegistry;
    public SurveyController(SurveyService surveyService, CompileSurveyResults compileSurveyResults, UserSessionRegistry userSessionRegistry) {
        this.surveyService = surveyService;
        this.compileSurveyResults = compileSurveyResults;
        this.userSessionRegistry = userSessionRegistry;
    }
    @GetMapping("/reveal_button")
    public String revealButton(Model model)
    {
        model.addAttribute("hidden_survey",true);
        return "index-chat-page";
    }

    @GetMapping()
    public String getResults(Model model)
    {
        Map<String,Long>surveyMap=compileSurveyResults.CompileSurveys();
        model.addAttribute("survey_results",surveyMap);
        return "surveyResults";
    }


    @PostMapping()
    public String submitSurvey(@RequestParam String exampleForm) throws IOException {
        surveyService.createSurvey(exampleForm);
        if(userSessionRegistry.getSize()==surveyService.getSurveys().size()&&userSessionRegistry.getSize()>0)
        {

            return "redirect:/survey/reveal_button";
        }
        return "index-chat-page";
    }

}
