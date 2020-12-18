package com.sg.superheroes.dao;

import com.sg.superheroes.model.SLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SLocation getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE idlocation = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SLocation> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public SLocation addLocation(SLocation location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, address, latitude, longitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(SLocation location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, address = ?, latitude = ?, longitude = ? "
                + "WHERE idlocation = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTING_LOCATION = "DELETE FROM sighting WHERE idlocation = ?";
        jdbc.update(DELETE_SIGHTING_LOCATION, id);

        final String DELETE_LOCATION = "DELETE FROM location WHERE idlocation = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    public static final class LocationMapper implements RowMapper<SLocation> {

        @Override
        public SLocation mapRow(ResultSet rs, int index) throws SQLException {
            SLocation location = new SLocation();
            location.setId(rs.getInt("idlocation"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getDouble("latitude"));
            location.setLongitude(rs.getDouble("longitude"));

            return location;
        }
    }
}
