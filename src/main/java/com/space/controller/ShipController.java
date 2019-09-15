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
import javax.validation.constraints.*;
import java.text.DecimalFormat;
import java.util.Calendar;
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
    public void delete(@PathVariable long id){
        shipService.deleteShip(id);
    }


    @PostMapping(value = "/rest/ships", consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public Ship save(@Valid @RequestBody Ship ship) throws IllegalArgumentException {
        validateShip(ship);
        return shipService.saveShip(ship);
    }

    @PostMapping(value = "/rest/ships/{id}", consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public Ship update(@RequestBody Ship ship, @PathVariable int id){
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
        System.out.println("createNotFoundResponse");
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    public void createBadResponse() {
        System.out.println("createBadResponse");
    }

    private void validateShip(Ship ship) throws IllegalArgumentException {
        boolean badRequest = false;
        if(ship == null){
            throw new IllegalArgumentException();
        }
        if (ship.getName() == null || ship.getName().isEmpty() || ship.getName().length() > 50){
            throw new IllegalArgumentException();
        }
        if ( ship.getPlanet() == null || ship.getPlanet().isEmpty() || ship.getPlanet().length() > 50 ){
            throw new IllegalArgumentException();
        }
        if (ship.getCrewSize() == null || ship.getCrewSize() < 1 || ship.getPlanet().length() > 9999 ){
            throw new IllegalArgumentException();
        }

        if(ship.getSpeed() == null){
            throw new IllegalArgumentException();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        double speed  = ship.getSpeed();


        if (speed < 0.01 || speed > 0.99 ){
            throw new IllegalArgumentException();
        }

        if(ship.getProdDate() == null){
            throw new IllegalArgumentException();
        }
        int prodDate = ship.getProdDate().toLocalDate().getYear();
        if (prodDate < 2800 || prodDate > 3019 ){
            throw new IllegalArgumentException();
        }

        if (badRequest){
            throw new IllegalArgumentException();
        }
    }

}
