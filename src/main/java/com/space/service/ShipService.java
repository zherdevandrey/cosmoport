package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {

    @Autowired
    ShipRepository shipRepository;

    public List<Ship> findShips(String name, String planet, double minSpeed, double maxSpeed){
        return shipRepository.findShips(name, planet, minSpeed, maxSpeed);
    }

}
