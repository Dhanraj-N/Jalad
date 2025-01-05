package com.franchiseworld.jalad.serviceimplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.model.BusinessUser;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.model.User;
import com.franchiseworld.jalad.model.Users;
import com.franchiseworld.jalad.modeldto.UsersDto;
import com.franchiseworld.jalad.repo.UsersRepository;
import com.franchiseworld.jalad.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;

@Override
public UsersDto updatePersonalUser(String currentUsername, UsersDto userDto) {
    // Fetch the current user details
    Optional<User> userOptional = userDetailsService.findByEmail(currentUsername);
    if (userOptional.isEmpty()) {
        return null; // or throw an exception
    }

    User currentUser = userOptional.get();

    // Fetch the corresponding Users entity
    Optional<Users> usersOptional = usersRepository.findByUserId(currentUser.getId());
    if (usersOptional.isEmpty()) {
        return null; // or throw an exception
    }

    Users usersEntity = usersOptional.get();

    // Update the personal user details
    if (usersEntity instanceof PersonalUser) {
        usersEntity.setFirstName(userDto.getFirstName());
        usersEntity.setLastName(userDto.getLastName());
//        usersEntity.setEmail(userDto.getEmail());

        // Save the updated Users entity
        Users updatedUser = usersRepository.save(usersEntity);

        // Convert back to UserDto
        return pconvertToDto(updatedUser);
    } else {
        return null; // Not a PersonalUser, handle accordingly
    }
}
//Method to convert Users entity to UserDto
private UsersDto pconvertToDto(Users users) {
 UsersDto dto = new UsersDto();
 dto.setId(users.getId());
 dto.setFirstName(users.getFirstName());
 dto.setLastName(users.getLastName());
// dto.setEmail(users.getEmail());

 return dto;	
}

@Override
public UsersDto updateBusinessUser(String currentUsername, UsersDto userDto) {
    // Fetch the current user details
    Optional<User> userOptional = userDetailsService.findByEmail(currentUsername);
    if (userOptional.isEmpty()) {
        return null; // or throw an exception
    }

    User currentUser = userOptional.get();

    // Fetch the corresponding Users entity
    Optional<Users> usersOptional = usersRepository.findByUserId(currentUser.getId());
    if (usersOptional.isEmpty()) {
        return null; // or throw an exception
    }

    BusinessUser usersEntity = (BusinessUser) usersOptional.get();

    // Update the Business user details
    if (usersEntity instanceof BusinessUser) {
        usersEntity.setFirstName(userDto.getFirstName());
        usersEntity.setLastName(userDto.getLastName());
//        usersEntity.setEmail(userDto.getEmail());
//        usersEntity.setPassword(userDto.getPassword());
        usersEntity.setCompanyName(userDto.getCompanyName());
        usersEntity.setContactNo(userDto.getContactNo());

        // Save the updated Users entity
        BusinessUser updatedUser = usersRepository.save(usersEntity);

        // Convert back to UserDto
        return bconvertToDto(updatedUser);
    } else {
        return null; 
    }
}
//Method to convert Users entity to UserDto
private UsersDto bconvertToDto(BusinessUser users) {

	UsersDto dto = new UsersDto();
	dto.setId(users.getId());
	dto.setFirstName(users.getFirstName());
	dto.setLastName(users.getLastName());
//	dto.setEmail(users.getEmail());
//	dto.setPassword(users.getPassword());
	dto.setCompanyName(users.getCompanyName());
	dto.setContactNo(users.getContactNo());

	return dto;	
	}

}
