//package com.franchiseworld.jalad.repo;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.franchiseworld.jalad.model.Admin;
//
//public interface AdminRepository extends JpaRepository<Admin, Long> {
//    Optional<Admin> findByEmailId(String emailId);
//}
package com.franchiseworld.jalad.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchiseworld.jalad.model.Admin;



public interface AdminRepository extends JpaRepository<Admin, Long> {

}
