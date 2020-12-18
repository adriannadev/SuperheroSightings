package com.sg.superheroes.dao;

import com.sg.superheroes.model.SLocation;
import com.sg.superheroes.model.Sighting;
import com.sg.superheroes.model.Superhero;

import java.time.LocalDate;
import java.util.List;

public interface SightingDao {
    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    List<Sighting> getSightingsForDate(LocalDate date);

    List<Sighting> getSightingsForSuperhero(Superhero superhero);

    List<Sighting> getSightingsForLocation(SLocation location);

}
