package com.space.repository;

import com.space.model.Ship;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShipRepository  {
    List<Ship> findShips(Map<String,String> requestParams);
    int countShips(Map<String,String> requestParams);

    Ship findShipById(long id);

    Ship saveShip(Ship ship);

    Ship updateShip(Ship ship);

    void deleteShip(Ship ship);
}
