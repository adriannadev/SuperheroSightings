package com.sg.superheroes.controller;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.dao.OrganisationDao;
import com.sg.superheroes.dao.SightingDao;
import com.sg.superheroes.dao.SuperheroDao;
import com.sg.superheroes.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;


    @GetMapping("/")
    public String displayFeed(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        if (sightings.size() > 10) {
            sightings = sightings.subList(sightings.size() - 10, sightings.size());
        }
        model.addAttribute("sightings", sightings);
        return "index";
    }
}
