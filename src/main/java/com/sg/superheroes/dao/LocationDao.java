package com.sg.superheroes.dao;

import com.sg.superheroes.model.SLocation;

import java.util.List;

public interface LocationDao {
    SLocation getLocationById(int id);

    List<SLocation> getAllLocations();

    SLocation addLocation(SLocation location);

    void updateLocation(SLocation location);

    void deleteLocationById(int id);

}
