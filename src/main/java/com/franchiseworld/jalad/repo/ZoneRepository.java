package com.franchiseworld.jalad.repo;

import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone,Long> {


    Optional<Zone> findByZoneCodeAndPassword(Long zoneCode, String password);

    Optional<Zone> findByName(String name);


    // admin
    @Query("SELECT z FROM Zone z WHERE z.city = :city AND z.state = :state")
    Zone findZoneByCityAndState(String city, String state);
}