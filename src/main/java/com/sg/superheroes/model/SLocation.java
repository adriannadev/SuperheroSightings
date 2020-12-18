package com.sg.superheroes.model;

import javax.validation.constraints.*;
import java.util.Objects;

public class SLocation {
    private int id;
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message = "Name must be less than 45 characters.")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters.")
    private String description;

    @NotBlank(message = "Address must not be empty.")
    @Size(max = 100, message = "Address must be less than 100 characters.")
    private String address;

    @Digits(integer = 8, fraction = 6, message = "Latitude can have maximum of 6 decimal places..")
    @Min(value = -90, message = "Latitude should not be less than -90")
    @Max(value = 90, message = "Latitude should not be greater than 90")
    private double latitude;

    @Digits(integer = 9, fraction = 6, message = "Longitude can have maximum of 6 decimal places..")
    @Min(value = -180, message = "Longitude should not be less than -180")
    @Max(value = 180, message = "Longitude should not be greater than 180")
    private double longitude;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SLocation sLocation = (SLocation) o;
        return id == sLocation.id &&
                Double.compare(sLocation.latitude, latitude) == 0 &&
                Double.compare(sLocation.longitude, longitude) == 0 &&
                Objects.equals(name, sLocation.name) &&
                Objects.equals(description, sLocation.description) &&
                Objects.equals(address, sLocation.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, latitude, longitude);
    }
}
