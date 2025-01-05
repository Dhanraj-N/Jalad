package com.franchiseworld.jalad.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.franchiseworld.jalad.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone,Long> {
	Optional<Zone> findByZoneCode(Long zoneCode);

    Optional<Zone> findByZoneCodeAndPassword(Long zoneCode, String password);

    Optional<Zone> findByName(String name);

 // Example: Fetch default zone by a specific condition (like default zone code)
    @Query("SELECT z FROM Zone z WHERE z.isDefault = true")
    List<Zone> findDefaultZone();
    // admin
    @Query("SELECT z FROM Zone z WHERE z.city = :city AND z.state = :state")
    Zone findZoneByCityAndState(String city, String state);
}