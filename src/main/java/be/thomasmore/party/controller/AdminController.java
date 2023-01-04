package be.thomasmore.party.controller;
import be.thomasmore.party.model.Party;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
import be.thomasmore.party.repositories.PartyRepository;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @ModelAttribute("party")
    public Party findParty(@PathVariable(required = false) Integer id) {
        logger.info("findParty " + id);
        if (id == null) return new Party();
        Optional<Party> optionalParty = partyRepository.findById(id);
        if (optionalParty.isPresent())
            return optionalParty.get();
        return null;
    }
    @GetMapping("/partyedit/{id}")
    public String partyEdit(Model model,
                            @PathVariable int id) {
        logger.info("partyEdit " + id);
        model.addAttribute("venues", venueRepository.findAll());
        model.addAttribute("artists", artistRepository.findAll());
        return "admin/partyedit";
    }
    @PostMapping("/partyedit/{id}")
    public String partyEditPost(Model model,
                                @PathVariable int id,
                                @Valid @ModelAttribute("party") Party party,
                                BindingResult bindingResult,
                                @RequestParam Integer venueId,
                                @RequestParam(required = false) Integer[] artistIds) {
        logger.info("partyEditPost " + id + " new name=" + party.getName() + ", date=" + party.getDate());
        logger.info("artists " + Arrays.toString(artistIds));
        if (bindingResult.hasErrors()) {
            model.addAttribute("venues", venueRepository.findAll());
            model.addAttribute("artists", artistRepository.findAll());
            party.setArtists(artistRepository.findByIdInIfNotNull(artistIds));
            return "admin/partyedit";
        }
        if (venueId != null && party.getVenue().getId() !=venueId) {
            party.setVenue(new Venue(venueId));
        }
        party.setArtists(artistRepository.findByIdInIfNotNull(artistIds));
        partyRepository.save(party);
        return "redirect:/partydetails/" + id;
    }
    @GetMapping("/partynew")
    public String partyNew(Model model) {
        logger.info("partyNew ");
        model.addAttribute("party", new Party());
        model.addAttribute("venues", venueRepository.findAll());
        model.addAttribute("artists", artistRepository.findAll());
        return "admin/partynew";
    }

    @PostMapping("/partynew")
    public String partyNewPost(Model model,
                               @Valid @ModelAttribute("party") Party party,
                               BindingResult bindingResult,
                               @RequestParam int venueId,
                               @RequestParam(required = false) Integer[] artistIds) {
        logger.info("partyNewPost new name=" + party.getName() + ", date=" + party.getDate());
        if (bindingResult.hasErrors()) {
            model.addAttribute("venues", venueRepository.findAll());
            model.addAttribute("artists", artistRepository.findAll());
            party.setArtists(artistRepository.findByIdInIfNotNull(artistIds));
            return "admin/partynew";
        }
        party.setVenue(new Venue(venueId));
        party.setArtists(artistRepository.findByIdInIfNotNull(artistIds));
        Party newParty = partyRepository.save(party);
        return "redirect:/partydetails/" + newParty.getId();
    }

}
