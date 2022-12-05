package be.thomasmore.party.controller;


import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;

    @GetMapping("/venuelist")
    public String venuelist(Model model) {
        boolean showFilters = false;
        Iterable<Venue> venues = venueRepository.findAll();

        model.addAttribute("venues", venues);
        model.addAttribute("showFilters",showFilters);
        model.addAttribute("aantal",venueRepository.count());
        return "venuelist";
    }
    @GetMapping("/venuelist/filter")
    public String filter(Model model) {
        boolean showFilters = true;
        Iterable<Venue> venues = venueRepository.findAll();

        model.addAttribute("venues", venues);
        model.addAttribute("showFilters",showFilters);
        model.addAttribute("aantal",venueRepository.count());
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/", "/venuedetails/{id}"})
    public String venueDetails(Model model, @PathVariable(required = false) Integer id) {

        Optional oVenue = null;
        Venue venue = null;
        int venueCount = 0;
        boolean idNull = false;

        venueCount = (int) venueRepository.count();

        if (id == null || id != (int) id) {
            id = 0;
        }
        if (id <= 0 || id > venueCount) {
            idNull = true;
        }

        oVenue = venueRepository.findById(id);
        if (oVenue.isPresent()) {
            venue = (Venue) oVenue.get();
        }

        int prevId = id - 1;
        if (prevId < 1) {
            prevId = venueCount;
        }

        int nextId = id + 1;
        if (nextId > venueCount) {
            nextId = 1;
        }

        model.addAttribute("nextId", nextId);
        model.addAttribute("prevId", prevId);
        model.addAttribute("venue", venue);
        model.addAttribute("idNull", idNull);
        return "venuedetails";
    }
}