package com.franchiseworld.jalad.serviceimplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.PersonalCourier;
import com.franchiseworld.jalad.model.PersonalUser;
import com.franchiseworld.jalad.repo.PersonalCourierRepository;
import com.franchiseworld.jalad.repo.PersonalUserRepository;
import com.franchiseworld.jalad.service.PersonalUserService;

@Service
public class PersonalUserServiceImpl implements PersonalUserService {

	@Autowired
    private PersonalUserRepository personalUserRepository;

    @Override
    public ResponseEntity<ApiResponse> savePersonalUser(PersonalUser personalUser) {
    	PersonalUser savedPersonalUser = personalUserRepository.save(personalUser);
        try {
        	return ResponseEntity.ok()
            		.body(new ApiResponse(savedPersonalUser, true, 200,"PersonalUser saved successfully"));
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, 500, "Internal Server Error"));
        }
        
    }
    
    @Override
    public ResponseEntity<ApiResponse> loginUser(String email, String password) {
        try {
            Optional<PersonalUser> existingUser = personalUserRepository.findByEmail(email);

            if (existingUser.isPresent()) {
                PersonalUser personalUser = existingUser.get();

                // Check if the password matches
                if (personalUser.getPassword().equals(password)) {
                    return ResponseEntity.ok()
                        .body(new ApiResponse(personalUser, true, 200, "Login successful"));
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
    
//    @Override
//    public ResponseEntity<ApiResponse> getPersonalUserById(int id) {
//        		try {
//                    Optional<PersonalUser> existingPersonalUser = personalUserRepository.findById(id);
//                    if (existingPersonalUser.isPresent()) {
//                    	PersonalUser personalUser = existingPersonalUser.get();
//                        return ResponseEntity.ok()
//                                .body(new ApiResponse(personalUser, true, 200, "PersonalUser Found Successfully"));
//                    }
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                            .body(new ApiResponse(false, 404, "Data Not found for id " + id));
//                } catch (Exception e) {
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .body(new ApiResponse(false, 500, "Internal Server Error"));
//                }
//    }
//
    @Override
    public ResponseEntity<ApiResponse> updatePersonalUser(long id, PersonalUser personalUserDetails) {
        
    	try {
    		Optional<PersonalUser> existingPersonalUser = personalUserRepository.findById(id);

            if (existingPersonalUser.isPresent()) {
            	PersonalUser personalUser = existingPersonalUser.get();
            	
            	personalUser.setFirstName(personalUserDetails.getFirstName());
            	personalUser.setLastName(personalUserDetails.getLastName());
            	personalUser.setEmail(personalUserDetails.getEmail());
                
                return ResponseEntity.ok()
                        .body(new ApiResponse(personalUserRepository.save(personalUser), true, 200, "User Updated Successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "User Not found for id " + id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, 500, "Internal Server Error"));
        }
    }
    
    //PersonalCourier
    
    @Autowired
    private PersonalCourierRepository personalCourierRepository;

    @Override
    public ResponseEntity<ApiResponse> savePersonalCourier(PersonalCourier personalCourier) {
        try {
            PersonalCourier savedCourier = personalCourierRepository.save(personalCourier);
            return ResponseEntity.ok()
                .body(new ApiResponse(savedCourier, true, 200, "Courier saved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, 500, "Internal Server Error"));
        }
    }
    
    //getPersonalCourierByID
    @Override
    public ResponseEntity<ApiResponse> getPersonalCourierById(Long id) {
        try {
            Optional<PersonalCourier> courier = personalCourierRepository.findById(id);
            if (courier.isPresent()) {
                return ResponseEntity.ok()
                    .body(new ApiResponse(courier.get(), true, 200, "Courier found successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "Courier not found for id " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, 500, "Internal Server Error"));
        }
    }
    //updatePersonalCourier
    @Override
    public ResponseEntity<ApiResponse> updatePersonalCourier(Long id, PersonalCourier personalCourierDetails) {
        try {
            Optional<PersonalCourier> existingCourier = personalCourierRepository.findById(id);
            if (existingCourier.isPresent()) {
                PersonalCourier courier = existingCourier.get();
                
                // Update the details
//                courier.setContactNo(personalCourierDetails.getContactNo());
                courier.setPickupAddress(personalCourierDetails.getPickupAddress());
                courier.setDeliveryAddress(personalCourierDetails.getDeliveryAddress());
                courier.setPackageDetails(personalCourierDetails.getPackageDetails());
                courier.setPackageCover(personalCourierDetails.getPackageCover());
                courier.setPackageSize(personalCourierDetails.getPackageSize());
                courier.setPickupDate(personalCourierDetails.getPickupDate());
                
                PersonalCourier updatedCourier = personalCourierRepository.save(courier);
                return ResponseEntity.ok()
                    .body(new ApiResponse(updatedCourier, true, 200, "Courier updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "Courier not found for id " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, 500, "Internal Server Error"));
        }
    }

    //GetAllCouriersByPhoneNumbeer
    @Override
    public ResponseEntity<ApiResponse> getCouriersByContactNo(Long contactNo) {
        try {
            List<PersonalCourier> couriers = personalCourierRepository.findByContactNo(contactNo);
            if (!couriers.isEmpty()) {
                return ResponseEntity.ok()
                    .body(new ApiResponse(couriers, true, 200, "Couriers found successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "No couriers found for contact number " + contactNo));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, 500, "Internal Server Error"));
        }
    }
}

