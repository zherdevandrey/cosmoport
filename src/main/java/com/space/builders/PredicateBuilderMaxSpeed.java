package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderMaxSpeed implements PredicateBuilderInterface {

    public static final String paramName = "maxSpeed";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        Double maxSpeed = Double.valueOf(paramValue);
        return criteriaBuilder.lessThan(shipRoot.get("speed"), maxSpeed);
    }
}
