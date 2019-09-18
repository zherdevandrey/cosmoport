package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateBuilderIsUsed implements PredicateBuilderInterface {

    public static final String paramName = "isUsed";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        boolean isUser = Boolean.valueOf(paramValue);
        return criteriaBuilder.equal(shipRoot.get("used"), isUser);
    }
}
