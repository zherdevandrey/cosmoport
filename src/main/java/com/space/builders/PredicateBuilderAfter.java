package com.space.builders;

import com.space.builders.interf.PredicateBuilderInterface;
import com.space.model.Ship;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PredicateBuilderAfter implements PredicateBuilderInterface {

    public static final String paramName = "after";

    @Override
    public Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder, Root<Ship> shipRoot) {
        Long after = Long.valueOf(paramValue);
        return criteriaBuilder.greaterThanOrEqualTo(shipRoot.get("prodDate"), new Date(after));
    }
}
