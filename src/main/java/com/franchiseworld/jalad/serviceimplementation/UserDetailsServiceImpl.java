package com.franchiseworld.jalad.serviceimplementation;


import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.model.BusinessUser;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.model.Role;
import com.franchiseworld.jalad.model.User;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.modeldto.UserDto;
import com.franchiseworld.jalad.repo.BusinessUserRepo;
import com.franchiseworld.jalad.repo.PersonalUserRepo;
import com.franchiseworld.jalad.repo.UserRepo;
import com.franchiseworld.jalad.repo.UsersRepository;
import com.franchiseworld.jalad.repo.ZoneRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{


	private final UsersRepository usersRepository;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonalUserRepo personalUserRepo;
    private final BusinessUserRepo businessUserRepo;
    private final ZoneRepository zoneRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository,
                       UserRepo userRepository,
                       PasswordEncoder passwordEncoder,
                       PersonalUserRepo personalUserRepo,
                       BusinessUserRepo businessUserRepo,
                       ZoneRepository zoneRepository) {
        this.usersRepository = usersRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.personalUserRepo = personalUserRepo;
        this.businessUserRepo = businessUserRepo;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // role assignments done in below code
        switch (user.get().getRole()) {
            case ADMIN:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case PERSONAL_USER:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PERSONAL_USER"));
                break;
            case BUSINESS_USER:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_BUSINESS_USER"));
                break;
            case ZONE:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ZONE"));
                break;
            default:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
        return new User(user.get().getEmail(),
                user.get().getPassword(),
                grantedAuthorities);
    }

    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean zoneExists(Long zoneCode) {
        return zoneRepository.findByZoneCode(zoneCode).isPresent();
    }


   // Create Business User
    public BusinessUser createBusinessUser(BusinessUser businessUser) {
        // Create User with encoded password
        User user = new User(businessUser.getEmail(),
                             passwordEncoder.encode(businessUser.getPassword()),
                             Collections.singletonList(new SimpleGrantedAuthority("ROLE_BUSINESS_USER")));
        user.setRole(Role.BUSINESS_USER);

        // Save User
        User savedUser = userRepository.save(user);

        // Associate User with BusinessUser and save
        businessUser.setUser(savedUser);
        return businessUserRepo.save(businessUser);
    }


    // Create Personal User
    public PersonalUser createPersonalUser(PersonalUser personalUser) {
        // Create User with encoded password
        User user = new User(personalUser.getEmail(),
                             passwordEncoder.encode(personalUser.getPassword()),
                             Collections.singletonList(new SimpleGrantedAuthority("ROLE_PERSONAL_USER")));
        user.setRole(Role.PERSONAL_USER);

        // Save User
        User savedUser = userRepository.save(user);

        // Associate User with PersonalUser and save
        personalUser.setUser(savedUser);
        return personalUserRepo.save(personalUser);
    }

//    public Zone createZone(Zone zone, String email, String password) {
//        // Create User with encoded password
//        User user = new User(email, passwordEncoder.encode(password),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ZONE")));
//        user.setRole(Role.ZONE);
//
//        // Save User
//        User savedUser = userRepository.save(user);
//
//        // Associate User with Employee
//        zone.setUser(savedUser);
//        return zoneRepository.save(zone);
//    }
    public Zone createZone(Zone zone, Long zoneCode, String password) {
        // Create User with encoded password (assuming you have a user entity associated with a Zone)
        User user = new User("zone_" + zoneCode.toString(),
                             passwordEncoder.encode(password),
                             Collections.singletonList(new SimpleGrantedAuthority("ROLE_ZONE")));
        user.setRole(Role.ZONE);

        // Save User
        User savedUser = userRepository.save(user);

        // Associate User with Zone and save
        zone.setUser(savedUser);
        return zoneRepository.save(zone);
    }


    public BusinessUser addUserBusiness(User user) {
        BusinessUser businessUser = new BusinessUser();
        businessUser.setUser(user);
        return businessUserRepo.save(businessUser);
    }

    public PersonalUser addUserInPersonal(User user) {
        PersonalUser personalUser = new PersonalUser();
        personalUser.setUser(user);
        return personalUserRepo.save(personalUser);
    }

    public Zone addUserInZone(User user) {
        Zone zone = new Zone();
        zone.setUser(user);
        return zoneRepository.save(zone);
    }

}