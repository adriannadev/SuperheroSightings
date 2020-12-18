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
public class LocationDaoDBTest {
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
    public void testAddGetLocation() {
        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        SLocation fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocations() {
        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        SLocation location2 = new SLocation();
        location2.setName("Test Name2");
        location2.setDescription("Test Description2");
        location2.setAddress("Test Address2");
        location2.setLatitude(23.129864);
        location2.setLongitude(23.129864);

        location2 = locationDao.addLocation(location2);

        List<SLocation> fromDao = locationDao.getAllLocations();

        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(location));
        assertTrue(fromDao.contains(location2));
    }

    @Test
    public void testUpdateLocation() {

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);
        SLocation fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);

        location.setAddress("New Address");
        location.setLatitude(33.12464);

        locationDao.updateLocation(location);

        fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);
        SLocation fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);


        locationDao.deleteLocationById(location.getId());

        fromDao = locationDao.getLocationById(location.getId());

        assertNull(fromDao);
    }
}