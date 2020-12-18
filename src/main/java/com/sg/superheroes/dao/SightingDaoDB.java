package com.sg.superheroes.dao;

import com.sg.superheroes.model.SLocation;
import com.sg.superheroes.model.Sighting;
import com.sg.superheroes.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE idsighting = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperhero(getSuperheroForSighting(id));
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT sh.* FROM superhero sh "
                + "JOIN sighting s ON s.idsuperhero = sh.idsuperhero WHERE s.idsighting = ?";
        return jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroDaoDB.SuperheroMapper(), id);
    }

    private SLocation getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON s.idlocation = l.idlocation WHERE s.idsighting = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateSuperheroAndLocation(sightings);
        return sightings;
    }

    private void associateSuperheroAndLocation(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setSuperhero(getSuperheroForSighting(sighting.getId()));
            sighting.setLocation(getLocationForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, idlocation, idsuperhero) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                Date.valueOf(sighting.getDate()),
                sighting.getLocation().getId(),
                sighting.getSuperhero().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET date = ?, idlocation = ?, idsuperhero = ? "
                + "WHERE idsighting = ?";
        jdbc.update(UPDATE_SIGHTING,
                Date.valueOf(sighting.getDate()),
                sighting.getLocation().getId(),
                sighting.getSuperhero().getId(),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE idsighting = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_DATE = "SELECT * FROM sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_DATE,
                new SightingMapper(), date);
        associateSuperheroAndLocation(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsForSuperhero(Superhero superhero) {
        final String SELECT_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM sighting WHERE idsuperhero= ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHERO,
                new SightingMapper(), superhero.getId());
        associateSuperheroAndLocation(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsForLocation(SLocation location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE idlocation= ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION,
                new SightingMapper(), location.getId());
        associateSuperheroAndLocation(sightings);
        return sightings;
    }


    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("idsighting"));
            sighting.setDate(rs.getDate("date").toLocalDate());

            return sighting;
        }
    }
}
