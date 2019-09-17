package com.space.repository;

import com.space.builders.*;
import com.space.builders.interf.PredicateBuilderInterface;
import com.space.controller.ShipOrder;
import com.space.model.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;

@Repository
@Transactional
public class ShipRepositoryImpl implements ShipRepository {

    @Autowired
    private EntityManager entityManager;
    private CriteriaQuery<Ship> criteriaQuery;
    private Root<Ship> shipRoot;
    private CriteriaBuilder criteriaBuilder;
    private Map<String, PredicateBuilderInterface> predicateMap;

    @Override
    public List<Ship> findShips(Map<String,String> requestParams) {
        Predicate[] predicates = createPredicateList(requestParams);
        criteriaQuery.where(predicates);
        String order = requestParams.get("order");
        if(order != null){
            String fieldName = ShipOrder.valueOf(order).getFieldName();
            criteriaQuery.orderBy(criteriaBuilder.asc(shipRoot.get(fieldName)));
        }
        TypedQuery<Ship> results = entityManager.createQuery(criteriaQuery);
        Integer pageSize = Integer.valueOf(requestParams.get("pageSize"));
        Integer pageNumber = Integer.valueOf(requestParams.get("pageNumber"));
        results.setFirstResult(pageSize * pageNumber);
        results.setMaxResults(pageSize);
        return results.getResultList();
    }

    @Override
    public int countShips(Map<String, String> requestParams) {
        Predicate[] predicates = createPredicateList(requestParams);
        criteriaQuery.where(predicates);
        return entityManager.createQuery(criteriaQuery).getResultList().size();
    }

    @Override
    public Ship findShipById(long id) {
        return entityManager.find(Ship.class, id);
    }

    @Override
    public Ship saveShip(Ship ship) {
        entityManager.persist(ship);
        entityManager.flush();
        return ship;
    }

    @Override
    public Ship updateShip(Ship ship, long id) {
        Ship updatedShip = entityManager.find(Ship.class, id);
        updateShip(updatedShip, ship);
        entityManager.persist(updatedShip);
        return updatedShip;
    }

    @Override
    public void deleteShip(Ship ship) {
        Ship mergedShip = entityManager.merge(ship);
        entityManager.remove(mergedShip);
    }

    private Predicate[] createPredicateList(Map<String,String> requestParams){
        ArrayList<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String,String> entry : requestParams.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();
            if(predicateMap.get(paramName) != null){
                Predicate predicate = predicateMap.get(paramName).createPredicate(paramValue, criteriaBuilder, shipRoot);
                predicates.add(predicate);
            }
        }
        return predicates.toArray(new Predicate[0]);
    }

    public void updateShip(Ship updatedShip, Ship ship){
        if(ship.getId() != 0) {
            updatedShip.setId(ship.getId());
        }
        if(ship.getName() != null) {
            updatedShip.setName(ship.getName());
        }
        if(ship.getPlanet() != null) {
            updatedShip.setPlanet(ship.getPlanet());
        }
        if(ship.getUsed() != null) {
            updatedShip.setUsed(ship.getUsed());
        }
        if(ship.getCrewSize() != null) {
            updatedShip.setCrewSize(ship.getCrewSize());
        }
        if(ship.getShipType() != null) {
            updatedShip.setShipType(ship.getShipType());
        }
        if(ship.getProdDate() != null) {
            updatedShip.setProdDate(ship.getProdDate());
        }
        if(ship.getRating() == null) {
            updatedShip.setRating(null);
        }
        if(ship.getRating() != null) {
            updatedShip.setRating(ship.getRating());
        }
        if(ship.getSpeed() != null) {
            updatedShip.setSpeed(ship.getSpeed());
        }
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Ship.class);
        shipRoot = criteriaQuery.from(Ship.class);
        predicateMap = new HashMap<>();
        predicateMap.put(PredicateBuilderName.paramName, new PredicateBuilderName());
        predicateMap.put(PredicateBuilderPlanet.paramName, new PredicateBuilderPlanet());
        predicateMap.put(PredicateBuilderAfter.paramName, new PredicateBuilderAfter());
        predicateMap.put(PredicateBuilderBefore.paramName, new PredicateBuilderBefore());
        predicateMap.put(PredicateBuilderIsUsed.paramName, new PredicateBuilderIsUsed());
        predicateMap.put(PredicateBuilderMinSpeed.paramName, new PredicateBuilderMinSpeed());
        predicateMap.put(PredicateBuilderMaxSpeed.paramName, new PredicateBuilderMaxSpeed());
        predicateMap.put(PredicateBuilderMinCrewSize.paramName, new PredicateBuilderMinCrewSize());
        predicateMap.put(PredicateBuilderMaxCrewSize.paramName, new PredicateBuilderMaxCrewSize());
        predicateMap.put(PredicateBuilderMinRating.paramName, new PredicateBuilderMinRating());
        predicateMap.put(PredicateBuilderMaxRating.paramName, new PredicateBuilderMaxRating());
        predicateMap.put(PredicateBuilderShipType.paramName, new PredicateBuilderShipType());
    }
}
