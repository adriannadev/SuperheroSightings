package com.sg.superheroes.controller;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.dao.OrganisationDao;
import com.sg.superheroes.dao.SightingDao;
import com.sg.superheroes.dao.SuperheroDao;
import com.sg.superheroes.model.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrganisationController {

    Set<ConstraintViolation<Organisation>> violations = new HashSet<>();

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;


    @GetMapping("organisations")
    public String displayOrganisations(Model model) {
        List<Organisation> organisations = organisationDao.getAllOrganisations();
        model.addAttribute("organisations", organisations);
        model.addAttribute("errors", violations);
        return "organisations";
    }

    @PostMapping("addOrganisation")
    public String addOrganisation(HttpServletRequest request) {
        Organisation organisation = new Organisation();
        organisation.setName(request.getParameter("name"));
        organisation.setDescription(request.getParameter("description"));
        organisation.setAddress(request.getParameter("address"));
        organisation.setPhone(request.getParameter("phone"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organisation);

        if (violations.isEmpty()) {
            organisationDao.addOrganisation(organisation);
        }
        return "redirect:/organisations";
    }

    @GetMapping("deleteOrganisation")
    public String deleteOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organisationDao.deleteOrganisationById(id);

        return "redirect:/organisations";
    }

    @GetMapping("editOrganisation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = organisationDao.getOrganisationById(id);

        model.addAttribute("organisation", organisation);
        return "editOrganisation";
    }

    @PostMapping("editOrganisation")
    public String performEditOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = organisationDao.getOrganisationById(id);

        organisation.setName(request.getParameter("name"));
        organisation.setDescription(request.getParameter("description"));
        organisation.setAddress(request.getParameter("address"));
        organisation.setPhone(request.getParameter("phone"));

        organisationDao.updateOrganisation(organisation);

        return "redirect:/organisations";
    }
}
