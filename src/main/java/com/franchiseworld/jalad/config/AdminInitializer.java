package com.franchiseworld.jalad.config;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.franchiseworld.jalad.model.Admin;
import com.franchiseworld.jalad.model.Role;
import com.franchiseworld.jalad.model.User;
import com.franchiseworld.jalad.repo.AdminRepository;
import com.franchiseworld.jalad.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AdminInitializer {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;


    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            // Check if the admin user already exists
            if (!userRepository.existsByEmail("admin@example.com")) {
                // Create a new admin user
                User adminUser = new User();
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("admin_password"));
                adminUser.setRole(Role.ADMIN);
                adminUser.setAuthorities(grantedAuthorities);
                userRepository.save(adminUser);
            }
            if (adminRepository.findById(1L).isPresent()) {
                log.info("Company already present with id 1");

            } else {
                Admin admin = new Admin();
                admin.setAdminId(1L);
               adminRepository.save(admin);

            }
        };
    }

}