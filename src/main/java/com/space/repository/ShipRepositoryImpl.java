package com.space.repository;

import com.space.model.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class ShipRepositoryImpl implements ShipRepository {

    @Autowired
    private EntityManager em;

    private CriteriaQuery<Ship> criteriaQuery;
    private Root<Ship> ship;
    private CriteriaBuilder criteriaBuilder;

    Map<String, PredicateBuilderA> predicateMap;

    @Override
    public List<Ship> findShips(Map<String,String> requestParams) {
        criteriaBuilder = em.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Ship.class);
        ship = criteriaQuery.from(Ship.class);
        Predicate[] predicates = createPredicateList(requestParams);
        criteriaQuery.where(predicates);
        return em.createQuery(criteriaQuery).getResultList();
    }
    @PostConstruct
    public void init() {
        predicateMap = new HashMap<>();
        predicateMap.put(PredicateBuilderName.paramName, new PredicateBuilderName());
        predicateMap.put(PredicateBuilderPlanet.paramName, new PredicateBuilderPlanet());
        predicateMap.put(PredicateBuilderAfter.paramName, new PredicateBuilderAfter());
    }

    private Predicate[] createPredicateList(Map<String,String> requestParams){
        ArrayList<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String,String> entry : requestParams.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();
            Predicate predicate = null;

            if(paramValue != null){

                predicate = predicateMap.get(paramName).createPredicate(paramValue, criteriaBuilder);
                if (paramName.equals("before")) {
                    Long before = Long.valueOf(paramValue);
                    predicate = criteriaBuilder.lessThan(ship.get("prodDate"), new Date(before));
                }
                if (paramName.equals("isUsed")) {
                    boolean isUser = Boolean.valueOf(paramValue);
                    predicate = criteriaBuilder.equal(ship.get("used"), isUser);
                }
                if (paramName.equals("minSpeed")) {
                    Double minSpeed = Double.valueOf(paramValue);
                    predicate = criteriaBuilder.greaterThan(ship.get("speed"), minSpeed);
                }
                if (paramName.equals("maxSpeed")) {
                    Double maxSpeed = Double.valueOf(paramValue);
                    predicate = criteriaBuilder.lessThan(ship.get("speed"), maxSpeed);
                }
                if (paramName.equals("minCrewSize")) {
                    Integer minCrewSize = Integer.valueOf(paramValue);
                    predicate = criteriaBuilder.greaterThan(ship.get("crewSize"), minCrewSize);
                }
                if (paramName.equals("maxCrewSize")) {
                    Integer maxCrewSize = Integer.valueOf(paramValue);
                    predicate = criteriaBuilder.lessThan(ship.get("crewSize"), maxCrewSize);
                }
                if (paramName.equals("minRating")) {
                    Double minRating = Double.valueOf(paramValue);
                    predicate = criteriaBuilder.greaterThan(ship.get("rating"), minRating);
                }
                if (paramName.equals("maxRating")) {
                    Double maxRating = Double.valueOf(paramValue);
                    predicate = criteriaBuilder.lessThan(ship.get("rating"), maxRating);
                }
            }

            if(predicate != null){
                predicates.add(predicate);
            }
        }
        return predicates;
    }

    class  PredicateBuilderA{

        Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder){

            return null;
        }
    }

    class PredicateBuilderName extends PredicateBuilderA {
        public static final String paramName = "name";

        Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.like(ship.get(paramName), "%" + paramValue + "%");
        }
    }

    class PredicateBuilderPlanet extends PredicateBuilderName {
        public static final String paramName = "planet";
    }

    class PredicateBuilderAfter extends PredicateBuilderA {
        public static final String paramName = "after";

        Predicate createPredicate(String paramValue, CriteriaBuilder criteriaBuilder) {
            Long after = Long.valueOf(paramValue);
            return criteriaBuilder.greaterThan(ship.get("prodDate"), new Date(after));
        }

    }
}
