package be.thomasmore.party.controller;


import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
import be.thomasmore.party.repositories.VenueRepository;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Controller
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;
    private Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @GetMapping("/artistlist")
    public String artistlist (Model model){
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists",artists);
        return "artistlist";
    }
    @GetMapping("/artistlist/filter")
    public String filter (Model model,@RequestParam(required = false) String artistName){
        Iterable<Artist> artists = null;
        if (artistName == null || artistName == "")
        {
            artists = artistRepository.findAll();
        }
        else{
            artists = artistRepository.findArtistByArtistNameIsContainingIgnoreCase(artistName);
        }
        boolean showFilters = true;
        model.addAttribute("artists",artists);
        model.addAttribute("showFilters",showFilters);
        model.addAttribute("aantal", artistRepository.count());
        return "artistlist";
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
}
