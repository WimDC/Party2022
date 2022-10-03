package be.thomasmore.party.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    private final int mySpecialNumber = 35;
    private final String[] venueNames = {"De Loods","De Club","De Hangar","Zapoi","Kuub","Cuba Libre"};

    @GetMapping(value = {"/","/home","/home/"})
    public String home(Model model) {
        model.addAttribute("mySpecialNumber",mySpecialNumber);
        return "home";
    }
    @GetMapping(value = {"/venuelist", "/venuelist/"})
    public String venuelist(Model model){
        model.addAttribute("venueNames",venueNames);
        return "venuelist";
    }
    @GetMapping(value={"/about","/about/"})
    public String about() {
        return "about";
    }
    @GetMapping(value = {"/venuedetails/{venueName}"})
    public String venueDetails(Model model, @PathVariable String venueName) {
        model.addAttribute("venueNames",venueNames);
        return "venueDetails";
    }
}