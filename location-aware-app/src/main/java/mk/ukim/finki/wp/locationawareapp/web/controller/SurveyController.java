package mk.ukim.finki.wp.locationawareapp.web.controller;

import mk.ukim.finki.wp.locationawareapp.service.CompileSurveyResults;
import mk.ukim.finki.wp.locationawareapp.service.SurveyService;
import mk.ukim.finki.wp.locationawareapp.service.UserService;
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
    private final CompileSurveyResults compileSurveyResults;
    private final UserService userService;

    public SurveyController(SurveyService surveyService, CompileSurveyResults compileSurveyResults, UserService userService) {
        this.surveyService = surveyService;
        this.compileSurveyResults = compileSurveyResults;
        this.userService = userService;
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
       int size_user=userService.findAll().size();
       int survey_size=surveyService.getSurveys().size();
        if(size_user==survey_size&&size_user>0)
        {

            return "redirect:/survey/reveal_button";
        }
        return "index-chat-page";
    }

}
