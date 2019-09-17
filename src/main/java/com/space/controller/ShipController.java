package com.space.controller;

import com.space.anotations.ValidateId;
import com.space.exception.ShipNotFoundException;
import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class ShipController {

    @Autowired
    ShipService shipService;

    @GetMapping(value = "/rest/ships")
    public List<Ship> ships(@RequestParam Map<String,String> requestParams){
        List<Ship> ships = shipService.findShips(requestParams);
        return ships;
    }

    @GetMapping(value = "/rest/ships/{id}")
    public Ship findShipById(@PathVariable("id") @ValidateId long id){
        Ship ship = shipService.findShipById(id);
        return ship;
    }

    @GetMapping(value = "/rest/ships/count")
    public int shipsCount(@RequestParam Map<String,String> requestParams){
        return shipService.countShips(requestParams);
    }

    @DeleteMapping(value = "/rest/ships/{id}")
    public void delete(@PathVariable @ValidateId long id){
        shipService.deleteShip(id);
    }


    @PostMapping(value = "/rest/ships")
    @ResponseBody
    public Ship save(@Valid @RequestBody Ship ship) throws IllegalArgumentException {
        validateForNullParams(ship);
        validateShip(ship);
        return shipService.saveShip(ship);
    }

    @PostMapping(value = "/rest/ships/{id}")
    @ResponseBody
    public Ship update(@RequestBody Ship ship, @PathVariable @ValidateId long id){
        validateShip(ship);
        return shipService.updateShip(ship, id);
    }

    @GetMapping(value = "/")
    public ModelAndView enter(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShipNotFoundException.class)
    public void createNotFoundResponse() {
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    public void createBadResponse() {
    }

    private void validateForNullParams(Ship ship){
        if (ship.getName() == null
            || ship.getPlanet() == null
            || ship.getSpeed() == null
            || ship.getProdDate() == null
            || ship.getCrewSize() == null
            || ship.getPlanet() == null)
            throw new IllegalArgumentException();
    }

    private void validateShip(Ship ship) throws IllegalArgumentException {
        if(ship.getName() != null) {
            if (ship.getName().isEmpty() || ship.getName().length() > 50) {
                throw new IllegalArgumentException();
            }
        }
        if(ship.getPlanet() != null) {
            if (ship.getPlanet().isEmpty() || ship.getPlanet().length() > 50) {
                throw new IllegalArgumentException();
            }
        }
        if(ship.getCrewSize() != null) {
            if (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999) {
                throw new IllegalArgumentException();
            }
        }
        if(ship.getSpeed() != null) {
            DecimalFormat df = new DecimalFormat("#.##");
            double speed = ship.getSpeed();
            if (speed < 0.01 || speed > 0.99) {
                throw new IllegalArgumentException();
            }
        }
        if(ship.getProdDate() != null) {
            int prodDate = ship.getProdDate().toLocalDate().getYear();
            if (prodDate < 2800 || prodDate > 3019) {
                throw new IllegalArgumentException();
            }
        }
    }

}
