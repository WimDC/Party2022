package be.thomasmore.party.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Controller
public class HomeController {
    private final int mySpecialNumber = 35;
    private final String[] venueNames = {"De Loods","De Club","De Hangar","Zapoi","Kuub","Cuba Libre","De Vanue"};

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
    @GetMapping(value = {"/venuedetails/{venueName}","/venuedetails"})
    public String venueDetails(Model model, @PathVariable(required=false) String venueName) {
        model.addAttribute("venueName",venueName);
        return "venuedetails";
    }
    @GetMapping("/pay")
    public String pay(Model model){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int day = today.getDay();
        String weekend ="";
        if (day == 6 || day == 0) {
            weekend = "Prettig weekend, je hebt het verdiend!";
        } else {
            weekend = "Voor je het weet is het weekend! ";
        }
        model.addAttribute("weekend",weekend);
        model.addAttribute("today",format.format(today));
        c.add(Calendar.DATE,5);
        Date paydate = c.getTime();
        model.addAttribute("paydate", format.format(paydate));
        return "pay";
    }
}
