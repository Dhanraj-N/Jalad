package com.franchiseworld.jalad.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchiseworld.jalad.model.PersonalCourier;

public interface PersonalCourierRepository extends JpaRepository<PersonalCourier, Long> {

	List<PersonalCourier> findByContactNo(Long contactNo);
}