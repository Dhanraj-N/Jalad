package com.franchiseworld.jalad.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchiseworld.jalad.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(long id);
    boolean existsByEmail(String mail);
}
