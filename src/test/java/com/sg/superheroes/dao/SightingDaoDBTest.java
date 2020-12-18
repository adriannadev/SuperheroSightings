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

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {

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
    public void testAddGetSighting() {
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings() {

        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name2");
        superhero2.setDescription("Test Description2");
        superhero2.setSuperpower("Test Superpower2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        SLocation location2 = new SLocation();
        location2.setName("Test Name2");
        location2.setDescription("Test Description2");
        location2.setAddress("Test Address2");
        location2.setLatitude(23.129862);
        location2.setLongitude(23.129862);

        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.parse("2020-03-10"));
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero2);

        sighting2 = sightingDao.addSighting(sighting2);
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        List<Sighting> listFromDao = sightingDao.getAllSightings();

        assertEquals(2, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertTrue(listFromDao.contains(sighting2));

    }

    @Test
    public void testUpdateSighting() {
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);

        sighting.setDate(LocalDate.parse("2019-09-15"));
        sightingDao.updateSighting(sighting);
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSightingById() {
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getId());
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetSightingsForDate() {
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name2");
        superhero2.setDescription("Test Description2");
        superhero2.setSuperpower("Test Superpower2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        SLocation location2 = new SLocation();
        location2.setName("Test Name2");
        location2.setDescription("Test Description2");
        location2.setAddress("Test Address2");
        location2.setLatitude(23.129862);
        location2.setLongitude(23.129862);

        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.parse("2019-09-12"));
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero2);

        sighting2 = sightingDao.addSighting(sighting2);
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        List<Sighting> listFromDao = sightingDao.getSightingsForDate(LocalDate.parse("2019-09-12"));
        assertEquals(2, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertTrue(listFromDao.contains(sighting2));

        sighting2.setDate(LocalDate.parse("2018-08-18"));
        sightingDao.updateSighting(sighting2);
        listFromDao = sightingDao.getSightingsForDate(LocalDate.parse("2019-09-12"));
        assertEquals(1, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertFalse(listFromDao.contains(sighting2));

    }

    @Test
    public void testGetSightingsForSuperhero() {
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name2");
        superhero2.setDescription("Test Description2");
        superhero2.setSuperpower("Test Superpower2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        SLocation location2 = new SLocation();
        location2.setName("Test Name2");
        location2.setDescription("Test Description2");
        location2.setAddress("Test Address2");
        location2.setLatitude(23.129862);
        location2.setLongitude(23.129862);

        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.parse("2019-09-12"));
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero2);

        sighting2 = sightingDao.addSighting(sighting2);
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        List<Sighting> listFromDao = sightingDao.getSightingsForSuperhero(superhero);
        assertEquals(1, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertFalse(listFromDao.contains(sighting2));

        sighting2.setSuperhero(superhero);
        sightingDao.updateSighting(sighting2);
        listFromDao = sightingDao.getSightingsForSuperhero(superhero);
        assertEquals(2, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertTrue(listFromDao.contains(sighting2));

    }

    @Test
    public void testGetSightingsForLocation() {

        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setSuperpower("Test Superpower");
        superhero = superheroDao.addSuperhero(superhero);

        SLocation location = new SLocation();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(23.129864);
        location.setLongitude(23.129864);

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.parse("2019-09-12"));
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name2");
        superhero2.setDescription("Test Description2");
        superhero2.setSuperpower("Test Superpower2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        SLocation location2 = new SLocation();
        location2.setName("Test Name2");
        location2.setDescription("Test Description2");
        location2.setAddress("Test Address2");
        location2.setLatitude(23.129862);
        location2.setLongitude(23.129862);

        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.parse("2019-09-12"));
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero2);

        sighting2 = sightingDao.addSighting(sighting2);
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        List<Sighting> listFromDao = sightingDao.getSightingsForLocation(location);
        assertEquals(1, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertFalse(listFromDao.contains(sighting2));

        sighting2.setLocation(location);
        sightingDao.updateSighting(sighting2);
        listFromDao = sightingDao.getSightingsForLocation(location);
        assertEquals(2, listFromDao.size());
        assertTrue(listFromDao.contains(sighting));
        assertTrue(listFromDao.contains(sighting2));

    }
}