package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderMinRating implements PredicateBuilderInterface {

    public static final String paramName = "minRating";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        Double minRating = Double.valueOf(paramValue);
        return criteriaBuilder.greaterThan(shipRoot.get("rating"), minRating);
    }
}
