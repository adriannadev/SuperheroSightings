package com.sg.superheroes.dao;

import com.sg.superheroes.model.Organisation;
import com.sg.superheroes.model.Superhero;
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
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE idsuperhero = ?";
            Superhero superhero = jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setOrganisations(getOrganisationsForSuperhero(id));
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Organisation> getOrganisationsForSuperhero(int id) {
        final String SELECT_ORGANISATIONS_FOR_SUPERHERO = "SELECT o.* FROM organisation o "
                + "JOIN organisationSuperhero os ON os.idorganisation = o.idorganisation WHERE os.idsuperhero= ?";
        return jdbc.query(SELECT_ORGANISATIONS_FOR_SUPERHERO, new OrganisationDaoDB.OrganisationMapper(), id);
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        final String GET_ALL_SUPERHEROES = "SELECT * FROM superhero";
        List<Superhero> superheroes = jdbc.query(GET_ALL_SUPERHEROES, new SuperheroMapper());
        associateOrganisations(superheroes);
        return superheroes;
    }

    private void associateOrganisations(List<Superhero> superheroes) {
        for (Superhero superhero : superheroes) {
            superhero.setOrganisations(getOrganisationsForSuperhero(superhero.getId()));
        }
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description, superpower) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        insertOrganisationSuperhero(superhero);
        return superhero;
    }

    private void insertOrganisationSuperhero(Superhero superhero) {
        final String INSERT_ORGANISATION_SUPERHERO = "INSERT INTO "
                + "organisationSuperhero(idorganisation, idsuperhero) VALUES(?,?)";
        for (Organisation org : superhero.getOrganisations()) {
            jdbc.update(INSERT_ORGANISATION_SUPERHERO,
                    org.getId(),
                    superhero.getId());
        }
    }


    @Override
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ?, " +
                "superpower = ? WHERE idsuperhero = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower(),
                superhero.getId());
        final String DELETE_ORGANISATION_SUPERHERO = "DELETE FROM organisationSuperhero WHERE idsuperhero = ?";
        jdbc.update(DELETE_ORGANISATION_SUPERHERO, superhero.getId());
        insertOrganisationSuperhero(superhero);
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHERO_ORGANISATION = "DELETE FROM organisationSuperhero WHERE idsuperhero = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANISATION, id);

        final String DELETE_SUPERHERO_SIGHTING = "DELETE FROM sighting WHERE idsuperhero = ?";
        jdbc.update(DELETE_SUPERHERO_SIGHTING, id);

        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE idsuperhero = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("idsuperhero"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            superhero.setSuperpower(rs.getString("superpower"));

            return superhero;
        }
    }
}
