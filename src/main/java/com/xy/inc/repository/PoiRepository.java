package com.xy.inc.repository;

import com.xy.inc.domain.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {

    @Query("SELECT p FROM Poi p WHERE p.name LIKE :name")
    Poi getPoiByName(@Param("name") String name);
}
