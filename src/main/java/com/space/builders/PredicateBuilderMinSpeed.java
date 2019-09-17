package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderMinSpeed implements PredicateBuilderInterface {

    public static final String paramName = "minSpeed";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        Double minSpeed = Double.valueOf(paramValue);
        return criteriaBuilder.greaterThanOrEqualTo(shipRoot.get("speed"), minSpeed);
    }
}
