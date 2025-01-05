package com.franchiseworld.jalad.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchiseworld.jalad.model.PersonalUser;

public interface PersonalUserRepo extends JpaRepository<PersonalUser,Long> {
}
