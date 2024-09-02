package com.franchiseworld.jalad.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchiseworld.jalad.model.PersonalUser;

public interface PersonalUserRepository extends JpaRepository<PersonalUser, Integer>{
    public Optional<PersonalUser> findByEmail(String email);
	public Optional<PersonalUser> findById(long id);
}
