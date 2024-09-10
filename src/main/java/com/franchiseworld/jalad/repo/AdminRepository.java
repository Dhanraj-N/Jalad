package com.franchiseworld.jalad.repo;

import com.franchiseworld.jalad.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmailId(String emailId);
}
