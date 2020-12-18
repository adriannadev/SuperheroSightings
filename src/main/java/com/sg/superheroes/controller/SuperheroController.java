package com.sg.superheroes.controller;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.dao.OrganisationDao;
import com.sg.superheroes.dao.SightingDao;
import com.sg.superheroes.dao.SuperheroDao;
import com.sg.superheroes.model.Organisation;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SuperheroController {

    Set<ConstraintViolation<Superhero>> violations = new HashSet<>();


    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        List<Organisation> organisations = organisationDao.getAllOrganisations();
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("organisations", organisations);
        model.addAttribute("errors", violations);
        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(HttpServletRequest request) {
        Superhero superhero = new Superhero();
        superhero.setName(request.getParameter("name"));
        superhero.setDescription(request.getParameter("description"));
        superhero.setSuperpower(request.getParameter("superpower"));

        String[] orgIds = request.getParameterValues("idOrganisation");

        List<Organisation> organisations = new ArrayList<>();
        if (orgIds != null) {
            for (String orgId : orgIds) {
                organisations.add(organisationDao.getOrganisationById(Integer.parseInt(orgId)));
            }
        }
        superhero.setOrganisations(organisations);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);

        if (violations.isEmpty()) {
            superheroDao.addSuperhero(superhero);
        }


        return "redirect:/superheroes";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superheroDao.deleteSuperheroById(id);

        return "redirect:/superheroes";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Organisation> organisations = organisationDao.getAllOrganisations();
        model.addAttribute("superhero", superhero);
        model.addAttribute("organisations", organisations);

        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performEditSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);

        superhero.setName(request.getParameter("name"));
        superhero.setDescription(request.getParameter("description"));
        superhero.setSuperpower(request.getParameter("superpower"));

        String[] orgIds = request.getParameterValues("idOrganisation");

        List<Organisation> organisations = new ArrayList<>();
        if (orgIds != null) {
            for (String orgId : orgIds) {
                organisations.add(organisationDao.getOrganisationById(Integer.parseInt(orgId)));
            }
        }

        superhero.setOrganisations(organisations);
        superheroDao.updateSuperhero(superhero);

        return "redirect:/superheroes";
    }
}
