package com.franchiseworld.jalad.serviceimplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessUser;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.model.Users;
import com.franchiseworld.jalad.repo.UsersRepository;
import com.franchiseworld.jalad.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersRepository usersRepository;


    
@Override
public Users savePersonalUsers(PersonalUser personalUser) {
	return usersRepository.save(personalUser);
}

@Override
public Users saveBusinessUsers(BusinessUser businessUser) {
	return usersRepository.save(businessUser);
}

@Override
public ResponseEntity<ApiResponse> loginUser(String email, String password) {
    try {
        Optional<Users> existingUser = usersRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            Users user = existingUser.get();

            // Check if the password matches
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok()
                    .body(new ApiResponse(user, true, 200, "Login successful"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, 401, "Invalid email or password"));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse(false, 404, "User not found with email: " + email));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse(false, 500, "Internal Server Error"));
    }
}

@Override
public PersonalUser updatePersonalUser(Long id, String firstName, String lastName, String email) {
    Optional<Users> optionalUser = usersRepository.findById(id);

    if (optionalUser.isPresent() && optionalUser.get() instanceof PersonalUser) {
        PersonalUser personalUser = (PersonalUser) optionalUser.get();
        personalUser.setFirstName(firstName);
        personalUser.setLastName(lastName);
        personalUser.setEmail(email);
        return usersRepository.save(personalUser); // Save updated PersonalUser
    } else {
        throw new IllegalArgumentException("PersonalUser not found with id: " + id);
    }
}

@Override
public BusinessUser updateBusinessUser(Long id, String firstName, String lastName, String email, String password, String companyName, Long contactNo) {
    Optional<Users> optionalUser = usersRepository.findById(id);

    if (optionalUser.isPresent() && optionalUser.get() instanceof BusinessUser) {
        BusinessUser businessUser = (BusinessUser) optionalUser.get();
        businessUser.setFirstName(firstName);
        businessUser.setLastName(lastName);
        businessUser.setEmail(email);
        businessUser.setPassword(password);
        businessUser.setCompanyName(companyName);
        businessUser.setContactNo(contactNo);
        return usersRepository.save(businessUser); // Save updated BusinessUser
    } else {
        throw new IllegalArgumentException("BusinessUser not found with id: " + id);
    }
}
}
