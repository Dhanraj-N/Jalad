package com.franchiseworld.jalad.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchiseworld.jalad.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	public Optional<Users> findByEmail(String email);
	Boolean existsByEmail(String email);
	public Optional<Users> findById(long id);
	Optional<Users> findByUserId(Long userId);
}
