package com.sg.superheroes.dao;

import com.sg.superheroes.model.Organisation;
import com.sg.superheroes.model.SLocation;
import com.sg.superheroes.model.Sighting;
import com.sg.superheroes.model.Superhero;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperheroDaoDBTest {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Before
    public void setUp() {
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for (Superhero superhero : superheroes) {
            superheroDao.deleteSuperheroById(superhero.getId());
        }

        List<Organisation> organisations = organisationDao.getAllOrganisations();
        for (Organisation org : organisations) {
            organisationDao.deleteOrganisationById(org.getId());
        }

        List<SLocation> locations = locationDao.getAllLocations();
        for (SLocation location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
    }

    @Test
    public void testAddGetSuperhero() {
        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(organisation);
        superhero.setOrganisations(organisations);

        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    public void testGetAllSuperheroes() {

        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(organisation);
        superhero.setOrganisations(organisations);

        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name2");
        superhero2.setDescription("Test Description2");
        superhero2.setSuperpower("Test Superpower2");
        superhero2.setOrganisations(organisations);
        superhero2 = superheroDao.addSuperhero(superhero2);

        fromDao = superheroDao.getSuperheroById(superhero2.getId());
        assertEquals(superhero2, fromDao);

        List<Superhero> listFromDao = superheroDao.getAllSuperheroes();

        assertEquals(2, listFromDao.size());
        assertTrue(listFromDao.contains(superhero));
        assertTrue(listFromDao.contains(superhero2));

    }

    @Test
    public void testUpdateSuperhero() {
        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(organisation);
        superhero.setOrganisations(organisations);

        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superhero.setSuperpower("New Superpower");
        superhero.setDescription("New Description");

        superheroDao.updateSuperhero(superhero);

        fromDao = superheroDao.getSuperheroById(superhero.getId());

        assertEquals(superhero, fromDao);
    }

    @Test
    public void testDeleteSuperheroById() {
        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(organisation);
        superhero.setOrganisations(organisations);

        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superheroDao.deleteSuperheroById(superhero.getId());

        fromDao = superheroDao.getSuperheroById(superhero.getId());

        assertNull(fromDao);
    }
}