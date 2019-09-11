package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipService {

    @Autowired
    ShipRepository shipRepository;

    public List<Ship> findShips(Map<String,String> requestParams){
        return shipRepository.findShips(requestParams);
    }

}
