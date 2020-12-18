package com.sg.superheroes.dao;

import com.sg.superheroes.model.Organisation;

import java.util.List;

public interface OrganisationDao {

    Organisation getOrganisationById(int id);

    List<Organisation> getAllOrganisations();

    Organisation addOrganisation(Organisation org);

    void updateOrganisation(Organisation org);

    void deleteOrganisationById(int id);
//    List<Organisation> getOrganisationsForMember(Superhero superhero);
}
