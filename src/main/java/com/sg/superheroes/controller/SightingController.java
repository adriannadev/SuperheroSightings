package com.sg.superheroes.controller;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.dao.OrganisationDao;
import com.sg.superheroes.dao.SightingDao;
import com.sg.superheroes.dao.SuperheroDao;
import com.sg.superheroes.model.SLocation;
import com.sg.superheroes.model.Sighting;
import com.sg.superheroes.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SightingController {

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        List<SLocation> locations = locationDao.getAllLocations();

        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        Sighting sighting = new Sighting();

        try {
            sighting.setDate(LocalDate.parse(request.getParameter("date")));
        } catch (DateTimeParseException e) {
            sighting.setDate(null);
        }


        String idSuperhero = request.getParameter("idsuperhero");
        String idLocation = request.getParameter("idlocation");

        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(idSuperhero)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(idLocation)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        List<SLocation> locations = locationDao.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);

        sighting.setDate(LocalDate.parse(request.getParameter("date")));

        String idSuperhero = request.getParameter("idSuperhero");
        String idLocation = request.getParameter("idLocation");

        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(idSuperhero)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(idLocation)));

        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }
}
