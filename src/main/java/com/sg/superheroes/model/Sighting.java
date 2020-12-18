package com.sg.superheroes.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

public class Sighting {
    private int id;

    @NotNull(message = "Please choose a valid date.")
    @Past(message = "Date must be in the past!")
    private LocalDate date;

    @NotNull(message = "Location cannot be null")
    private SLocation location;

    @NotNull(message = "Superhero cannot be null")
    private Superhero superhero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SLocation getLocation() {
        return location;
    }

    public void setLocation(SLocation location) {
        this.location = location;
    }

    public Superhero getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id &&
                Objects.equals(date, sighting.date) &&
                Objects.equals(location, sighting.location) &&
                Objects.equals(superhero, sighting.superhero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, location, superhero);
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "id=" + id +
                ", date=" + date +
                ", location=" + location +
                ", superhero=" + superhero +
                '}';
    }
}
