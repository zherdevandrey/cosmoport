package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderMaxRating implements PredicateBuilderInterface {

    public static final String paramName = "maxRating";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        Double maxRating = Double.valueOf(paramValue);
        return criteriaBuilder.lessThanOrEqualTo(shipRoot.get("rating"), maxRating);
    }
}
