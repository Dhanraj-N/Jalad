package com.franchiseworld.jalad.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.dao.AuthenticationRequest;
import com.franchiseworld.jalad.dao.JwtResponse;
import com.franchiseworld.jalad.model.BusinessUser;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.model.Role;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.serviceimplementation.JwtService;
import com.franchiseworld.jalad.serviceimplementation.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userService;
// log in

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        System.out.println("qqqq");
        log.info("Authenticating user with email: {}",authenticationRequest.getEmail());
        log.info("Authenticating user with password: {}",authenticationRequest.getPassword());
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            System.out.println("wwww");
            log.error("Invalid credentials for user: {}", authenticationRequest.getEmail());
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    /*    authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());*/

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signup/personal")
    public ResponseEntity<?> registerPersonalUser(@RequestBody PersonalUser personalUser) {
        if (userService.userExists(personalUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken.");
        }

        PersonalUser createdPersonalUser = userService.createPersonalUser(personalUser);
        String token = jwtService.generateToken(createdPersonalUser.getUser().getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("PersonalUser created with id " + createdPersonalUser.getId() + " token is " + token);
    }

    @PostMapping("/signup/business")
    public ResponseEntity<?> registerBusinessUser(@RequestBody BusinessUser businessUser) {
        if (userService.userExists(businessUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken.");
        }

        BusinessUser createdBusinessUser = userService.createBusinessUser(businessUser);
        String token = jwtService.generateToken(createdBusinessUser.getUser().getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("BusinessUser created with id " + createdBusinessUser.getId() + " token is " + token);
    }


    @PostMapping("/signup/zone")
    public ResponseEntity<?> registerZone(@RequestBody Zone zone) {
        if (userService.zoneExists(zone.getZoneCode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Zone code is already taken.");
        }

        Zone createdZone = userService.createZone(zone, zone.getZoneCode(), zone.getPassword());
        String token = jwtService.generateToken(createdZone.getUser().getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Zone created with id " + createdZone.getZoneId() + " token is " + token);
    }


    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private boolean isSignupAllowed(Role requestedRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        String currentUserRole = userService.findByEmail(currentUser.getUsername()).get().getRole().name();
        log.info("Currunt user role is " + currentUserRole);
        return switch (currentUserRole) {
            case "ADMIN" ->
                // Admin can sign up PerosnlaUser, BusinessUser, Zone
                    requestedRole == Role.PERSONAL_USER || requestedRole == Role.BUSINESS_USER ||
                            requestedRole == Role.ZONE;
            case "PERSONAL_USER" ->
                // HR can sign up Distributor, Employee
                    requestedRole == Role.PERSONAL_USER ;
            case "BUSINESS_USER" ->
                // Distributor can sign up Retailer
                    requestedRole == Role.BUSINESS_USER;

            default -> false;
        };
    }
}