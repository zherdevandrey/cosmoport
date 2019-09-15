package com.space.service;

import com.space.exception.ShipNotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShipService {

    @Autowired
    ShipRepository shipRepository;

    public List<Ship> findShips(Map<String,String> requestParams){
        if(requestParams.get("pageSize") == null){
            requestParams.put("pageSize", "3");
        }
        if(requestParams.get("pageNumber") == null){
            requestParams.put("pageNumber", "0");
        }
        return shipRepository.findShips(requestParams);
    }

    public Ship findShipById(long id){
        Ship ship = shipRepository.findShipById(id);
        if(ship == null){
            throw new ShipNotFoundException();
        }
        return ship;
    }

    public int countShips(Map<String,String> requestParams){
        return shipRepository.countShips(requestParams);
    }

    public Ship saveShip(Ship ship) {
        if(ship.getUsed() == null){
            ship.setUsed(false);
        }
        double rating = calculateRating(ship);
        ship.setRating(rating);
        return shipRepository.saveShip(ship);
    }

    private double calculateRating(Ship ship){
        double speed = ship.getSpeed();
        int productionYear = ship.getProdDate().toLocalDate().getYear();
        double k = ship.getUsed() == false ? 1.0 : 0.5;
        double rating = 80 * speed * k / (3019 - productionYear +1 );
        return rating;
    }

    public void deleteShip(long id) {
         shipRepository.deleteShip(findShipById(id));
    }

    public Ship updateShip(Ship ship, int id) {
        if(shipRepository.findShipById(id) == null){
            throw new ShipNotFoundException();
        }
        ship.setId(id);
        return shipRepository.updateShip(ship);
    }

}
