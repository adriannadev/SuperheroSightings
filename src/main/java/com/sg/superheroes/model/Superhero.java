package com.sg.superheroes.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Superhero {
    private int id;
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message = "Name must be less than 45 characters.")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters.")
    private String description;

    @NotBlank(message = "Superpower must not be empty.")
    @Size(max = 45, message = "Superpower must be less than 45 characters.")
    private String superpower;

    private List<Organisation> organisations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superhero superhero = (Superhero) o;
        return id == superhero.id &&
                Objects.equals(name, superhero.name) &&
                Objects.equals(description, superhero.description) &&
                Objects.equals(superpower, superhero.superpower) &&
                Objects.equals(organisations, superhero.organisations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, superpower, organisations);
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", superpower='" + superpower + '\'' +
                ", organisations=" + organisations +
                '}';
    }
}
