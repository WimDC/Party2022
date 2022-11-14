package be.thomasmore.party.controller;


import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
import be.thomasmore.party.repositories.VenueRepository;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Controller
public class HomeController {
    private final int mySpecialNumber = 35;
    private final String [] venuenames = {"Carré", "Zillion", "Cherrymoon", "Boccaccio", "Carat"};
    private final Venue [] venues = {
            new Venue("Carré","Website Carré",100,true,true,false,false,"Willebroek",5),
            new Venue("Zillion","Website Zillion",250,true,true,false,false,"Antwerpen",1),
            new Venue("Cherrymoon","Website Cherrymoon",150,true,true,false,false,"Knokke",4),
            new Venue("Boccaccio","Website Boccaccio",125,false,false,true,true,"Ergens",10),
            new Venue("Carat","Website Carat",225,false,true,false,true,"Waarschijnlijk",15)
    };
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping(value = {"/", "/home", "/home/"})
    public String home (Model model){
        model.addAttribute("mySpecialNumber",mySpecialNumber);
        return "home";
    }

    @GetMapping("/pay")
    public String pay(Model model){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);

        Boolean weekend = false;
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY )
        {
            weekend = true;
        }


        c.add(Calendar.DATE,5);
        Date paydate = c.getTime();



        model.addAttribute("paydate", format.format(paydate));
        model.addAttribute("weekend",weekend);
        return "pay";
    }

    @GetMapping("/about")
    public String about (){
        return "about";
    }

    @GetMapping("/venuelist")
    public String venuelist (Model model){
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
        return "venuelist";
    }
    @GetMapping("/artistlist")
    public String artistlist (Model model){
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists",artists);
        return "artistlist";
    }

    @GetMapping({"/venuedetails","/venuedetails/","/venuedetails/{venuename}"})
    public String venuedetails(Model model, @PathVariable(required = false) String venuename){
        model.addAttribute("venuename",venuename);
        return "venuedetails";
    }

    @GetMapping({"/venuedetailsbyindex","/venuedetailsbyindex/","/venuedetailsbyindex/{venueindex}"})
    public String venuedetailsbyindex(Model model, @PathVariable(required = false) String venueindex){
        Venue venue = null;
        if(venueindex !=null && Integer.parseInt(venueindex)%1 == 0 && Integer.parseInt(venueindex)>= 0 && Integer.parseInt(venueindex)< venuenames.length )
        {
            //get venue data here
            venue = venues[Integer.parseInt(venueindex)];
        }

        int prevIndex = Integer.parseInt(venueindex)-1;
        if(prevIndex<0){
            prevIndex = venuenames.length - 1;
        }

        int nextIndex = Integer.parseInt(venueindex)+1;
        if(nextIndex >venuenames.length-1)
        {
            nextIndex = 0;
        }

        model.addAttribute("venue",venue);
        model.addAttribute("prevIndex", prevIndex);
        model.addAttribute("nextIndex", nextIndex);
        return "venuedetailsbyindex";
    }

    @GetMapping({"/venuedetailsbyid","/venuedetailsbyid/{id}"})
    public String venueDetailsById(Model model, @PathVariable(required = false) Integer id) {

        Optional oVenue = null;
        Venue venue = null;
        int venueCount = 0;
        boolean idNull = false;

        venueCount = (int) venueRepository.count();

        if(id == null || id != (int) id){
            id = 0;
        }
        if(id <= 0 || id > venueCount){
            idNull = true;
        }

        oVenue = venueRepository.findById(id);
        if (oVenue.isPresent()) {
            venue = (Venue) oVenue.get();
        }

        int prevId = id-1 ;
        if(prevId<1){
            prevId = venueCount;
        }

        int nextId = id+1;
        if(nextId>venueCount){
            nextId = 1;
        }

        model.addAttribute("nextId", nextId);
        model.addAttribute("prevId",prevId);
        model.addAttribute("venue", venue);
        model.addAttribute("idNull",idNull);
        return "venuedetailsbyid";
    }
    @GetMapping({"/artistdetailsbyid","/artistdetailsbyid/{id}"})
    public String artistDetailsById(Model model, @PathVariable(required = false) Integer id) {

        Optional oArtist = null;
        Artist artist = null;
        int artistCount = 0;
        boolean idNull = false;

        artistCount = (int) artistRepository.count();

        if(id == null || id != (int) id){
            id = 0;
        }
        if(id <= 0 || id > artistCount){
            idNull = true;
        }

        oArtist = artistRepository.findById(id);
        if (oArtist.isPresent()) {
            artist = (Artist) oArtist.get();
        }

        int prevId = id-1 ;
        if(prevId<1){
            prevId = artistCount;
        }

        int nextId = id+1;
        if(nextId>artistCount){
            nextId = 1;
        }

        model.addAttribute("nextId", nextId);
        model.addAttribute("prevId",prevId);
        model.addAttribute("artist", artist);
        model.addAttribute("idNull",idNull);
        return "artistdetailsbyid";
    }
    @GetMapping("venuelist/outdoor/yes")
    public String venuelistOutdoorYes (Model model)
    {
        Iterable<Venue> venues = venueRepository.findByOutdoor(true);
        model.addAttribute("venues", venues);
        return "venuelist";
    }
    @GetMapping("venuelist/outdoor/no")
    public String venuelistOutdoorNo (Model model)
    {
        Iterable<Venue> venues = venueRepository.findByOutdoor(false);
        model.addAttribute("venues", venues);
        return "venuelist";
    }
}
