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
public class OrganisationDaoDB implements OrganisationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organisation getOrganisationById(int id) {
        try {
            final String SELECT_ORGANISATION_BY_ID = "SELECT * FROM organisation WHERE idorganisation = ?";
            Organisation org = jdbc.queryForObject(SELECT_ORGANISATION_BY_ID, new OrganisationMapper(), id);
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Superhero> getSuperheroesForOrganisation(int id) {
        final String SELECT_SUPERHEROES_FOR_ORGANISATION = "SELECT sh.* FROM superhero sh "
                + "JOIN organisationSuperhero os ON os.idsuperhero= sh.idsuperhero WHERE os.idorganisation = ?";
        return jdbc.query(SELECT_SUPERHEROES_FOR_ORGANISATION, new SuperheroDaoDB.SuperheroMapper(), id);
    }

    @Override
    public List<Organisation> getAllOrganisations() {
        final String SELECT_ALL_ORGANISATIONS = "SELECT * FROM organisation";
        List<Organisation> organisations = jdbc.query(SELECT_ALL_ORGANISATIONS, new OrganisationMapper());
        return organisations;
    }


    @Override
    @Transactional
    public Organisation addOrganisation(Organisation org) {
        final String INSERT_STUDENT = "INSERT INTO organisation(name, description, address, phone) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_STUDENT,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getPhone());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        return org;
    }


    @Override
    public void updateOrganisation(Organisation org) {
        final String UPDATE_ORGANISATION = "UPDATE organisation SET name = ?, description = ?, address = ?, phone = ? "
                + "WHERE idorganisation = ?";
        jdbc.update(UPDATE_ORGANISATION,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getPhone(),
                org.getId());
        final String DELETE_ORGANISATION_SUPERHERO = "DELETE FROM organisationSuperhero WHERE idorganisation = ?";
        jdbc.update(DELETE_ORGANISATION_SUPERHERO, org.getId());
    }

    @Override
    @Transactional
    public void deleteOrganisationById(int id) {
        final String DELETE_ORGANISATION_SUPERHERO = "DELETE FROM organisationSuperhero WHERE idorganisation = ?";
        jdbc.update(DELETE_ORGANISATION_SUPERHERO, id);

        final String DELETE_ORGANISATION = "DELETE FROM organisation WHERE idorganisation = ?";
        jdbc.update(DELETE_ORGANISATION, id);
    }

//    @Override
//    public List<Organisation> getOrganisationsForMember(Superhero superhero){
//        final String SELECT_ORGANISATIONS_FOR_SUPERHERO = "SELECT o.* FROM organisation o JOIN "
//                + "organisationSuperhero os ON os.idorganisation = o.idorganisation WHERE os.idsuperhero = ?";
//        List<Organisation> organisations = jdbc.query(SELECT_ORGANISATIONS_FOR_SUPERHERO,
//                new OrganisationMapper(), superhero.getId());
//        associateMembers(organisations);
//        return organisations;
//    }

    public static final class OrganisationMapper implements RowMapper<Organisation> {

        @Override
        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            Organisation organisation = new Organisation();
            organisation.setId(rs.getInt("idorganisation"));
            organisation.setName(rs.getString("name"));
            organisation.setDescription(rs.getString("description"));
            organisation.setAddress(rs.getString("address"));
            organisation.setPhone(rs.getString("phone"));

            return organisation;
        }
    }
}
