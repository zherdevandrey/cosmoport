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
        if(ship.getUsed() == null) {
            ship.setUsed(false);
        }
        calculateRating(ship);
        return shipRepository.saveShip(ship);
    }

    public void deleteShip(long id) {
         shipRepository.deleteShip(findShipById(id));
    }

    public Ship updateShip(Ship ship, long id) {
        if(shipRepository.findShipById(id) == null){
            throw new ShipNotFoundException();
        }
        calculateRating(ship);
        Ship updated = shipRepository.updateShip(ship, id);
        if(updated.getRating() == null){
            calculateRating(updated);
        }
        return shipRepository.updateShip(updated, id);
    }

    private void calculateRating(Ship ship){
        if(ship.getSpeed() != null && ship.getProdDate() !=null && ship.getUsed() != null) {
            double speed = ship.getSpeed();
            int productionYear = ship.getProdDate().toLocalDate().getYear();
            double k = ship.getUsed() == false ? 1.0 : 0.5;
            double rating = 80 * speed * k / (3019 - productionYear + 1);
            rating = roundDouble(rating);
            ship.setRating(rating);
        }
    }

    private double roundDouble(double rating){
        double val = Math.round(rating*100);
        val = (double)((int) val);
        return val /100;
    }
}
