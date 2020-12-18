package com.sg.superheroes.dao;

import com.sg.superheroes.model.Superhero;

import java.util.List;

public interface SuperheroDao {

    Superhero getSuperheroById(int id);

    List<Superhero> getAllSuperheroes();

    Superhero addSuperhero(Superhero superhero);

    void updateSuperhero(Superhero superhero);

    void deleteSuperheroById(int id);

}
