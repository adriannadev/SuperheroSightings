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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganisationDaoDBTest {

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
    public void testGetAllOrganisations() {

        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);

        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
        assertEquals(organisation, fromDao);

        Organisation organisation2 = new Organisation();
        organisation2.setName("Test Organisation2");
        organisation2.setDescription("Test Description2");
        organisation2.setAddress("Test Address2");
        organisation2.setPhone("07610101020");

        organisation2 = organisationDao.addOrganisation(organisation2);

        fromDao = organisationDao.getOrganisationById(organisation2.getId());
        assertEquals(organisation2, fromDao);

        List<Organisation> listFromDao = organisationDao.getAllOrganisations();
        assertEquals(2, listFromDao.size());
        assertTrue(listFromDao.contains(organisation));
        assertTrue(listFromDao.contains(organisation2));
    }

    @Test
    public void testAddGetOrganisation() {


        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
        assertEquals(organisation, fromDao);
    }

    @Test
    public void testUpdateOrganisation() {

        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
        assertEquals(organisation, fromDao);

        organisation.setDescription("New Description");
        organisation.setPhone("07644551100");
        organisationDao.updateOrganisation(organisation);
        fromDao = organisationDao.getOrganisationById(organisation.getId());
        assertEquals(organisation, fromDao);
    }

    @Test
    public void testDeleteOrganisationById() {

        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation.setDescription("Test Description");
        organisation.setAddress("Test Address");
        organisation.setPhone("07610101000");

        organisation = organisationDao.addOrganisation(organisation);
        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
        assertEquals(organisation, fromDao);

        organisationDao.deleteOrganisationById(organisation.getId());
        fromDao = organisationDao.getOrganisationById(organisation.getId());
        assertNull(fromDao);
    }
}