package be.thomasmore.party.controller;


import be.thomasmore.party.model.Party;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.PartyRepository;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private PartyRepository partyRepository;
    private Logger logger = LoggerFactory.getLogger(VenueController.class);

    @GetMapping("/venuelist")
    public String venuelist(Model model) {
        boolean showFilters = false;
        Iterable<Venue> venues = venueRepository.findAll();

        model.addAttribute("venues", venues);
        model.addAttribute("showFilters",showFilters);
        model.addAttribute("aantal",venueRepository.count());
        return "venuelist";
    }
    @GetMapping({"/venuelist/filter"})
    public String venueListWithFilter(Model model,
                                      @RequestParam(required = false) Integer minCapacity,
                                      @RequestParam(required = false) Integer maxCapacity,
                                      @RequestParam(required = false) Integer maxDistance,
                                      @RequestParam(required = false) String foodProvided,
                                      @RequestParam(required = false) String indoor,
                                      @RequestParam(required = false) String outdoor) {
        logger.info(String.format("venueListFilter min=%d, max=%d, distance=%d, filterFood=%s, filterIndoor=%s, filterOutdoor=%s",
                minCapacity, maxCapacity, maxDistance, foodProvided, indoor, outdoor));

        List<Venue> venues = venueRepository.findByFilter(minCapacity, maxCapacity, maxDistance,
                filterStringToBoolean(foodProvided), filterStringToBoolean(indoor), filterStringToBoolean(outdoor));

        model.addAttribute("venues", venues);
        model.addAttribute("nrOfVenues", venues.size());
        model.addAttribute("showFilters", true);
        model.addAttribute("minCapacity", minCapacity);
        model.addAttribute("maxCapacity", maxCapacity);
        model.addAttribute("maxDistance", maxDistance);
        model.addAttribute("filterFood", foodProvided);
        model.addAttribute("filterIndoor", indoor);
        model.addAttribute("filterOutdoor", outdoor);

        return "venuelist";
    }

    private Boolean filterStringToBoolean(String filterString) {
        return (filterString == null || filterString.equals("all")) ? null : filterString.equals("yes");
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
            Iterable<Party> parties = partyRepository.findByVenue(venue);
            model.addAttribute("parties", parties);
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