package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {

    List<Ship> shipsList;

    @Autowired
    ShipService shipService;


    @GetMapping(value = "/ships")
    public List<Ship> ships(@RequestParam Map<String,String> requestParams){

        List<Ship> ships = shipService.findShips(requestParams);

        return ships;
    }

    @GetMapping(value = "/")
    public ModelAndView enter(){

        return new ModelAndView("index");
    }



    @PostMapping
    public void init (){
        shipsList = new ArrayList<>();
    }

}
