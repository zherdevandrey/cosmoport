package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("SELECT s FROM Ship s WHERE ((s.name = :name ) " +
            "and (:planet is null or s.planet = :planet)) " +
            "and (:speedMin is null or s.speed > :speedMin) and (:speedMax is null or s.speed < :speedMax)")
    List<Ship> findShips(@Param("name") String name,
                         @Param("planet") String planet,
                         @Param("speedMin") double speedMin, @Param("speedMax") double speedMax);


}
