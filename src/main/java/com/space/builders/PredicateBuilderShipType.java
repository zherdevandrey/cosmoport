package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderShipType implements PredicateBuilderInterface {
    public static final String paramName = "shipType";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        return criteriaBuilder.equal(shipRoot.get(paramName), paramValue);
    }
}
