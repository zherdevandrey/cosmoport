package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderPlanet implements PredicateBuilderInterface {
    public static final String paramName = "planet";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        return criteriaBuilder.like(shipRoot.get(paramName), "%" + paramValue + "%");
    }
}
