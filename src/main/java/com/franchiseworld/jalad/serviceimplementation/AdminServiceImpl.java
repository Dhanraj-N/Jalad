package com.franchiseworld.jalad.serviceimplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Admin;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.repo.AdminRepository;
import com.franchiseworld.jalad.repo.ZoneRepository;
import com.franchiseworld.jalad.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
   @Autowired
   private ZoneRepository zoneRepository;
        @Autowired
    private AdminRepository adminRepository;

    @Override
    public ResponseEntity<ApiResponse> findZoneByCityAndState(String city, String state) {
        try {
            Zone existingZone = zoneRepository.findZoneByCityAndState(city,state);
            if (existingZone!=null)
            {
                return ResponseEntity.ok()
                        .body(new ApiResponse(existingZone, true, 200, "Zone Found"));

         }
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(new ApiResponse(false, 404, "Zone not found with " + city +"and "+state));

       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, 500, "internal server error"));

     }

    }
   @Override
    public ResponseEntity<ApiResponse> createAdmin(Admin admin) {
        try {
           return ResponseEntity.ok()
                  .body(new ApiResponse(adminRepository.save(admin), true, 200, "Admin Created Successfully"));
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse(false, 500, "Internal server error"));
      }
    }

   /* @Override
    public ResponseEntity<ApiResponse> updateAdmin(Admin admin) {
       try {
            Optional<Admin> existingAdmin = adminRepository.findById(admin.getAdminId());
          if (existingAdmin.isPresent()) {
               Admin present = existingAdmin.get();
              if (admin.getPassword().equals(present.getPassword())) {
                   return ResponseEntity.ok()
                           .body(new ApiResponse(adminRepository.save(admin), true, 200, "Admin updated"));
               } else {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST) .body(new ApiResponse(false, 400, "Invalide Password"));
                }
           } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, 404, "Admin Not Found" + admin.getAdminId()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, 500, "Internal server error"));
        }
    }*/

    /*@Override
    public ResponseEntity<ApiResponse> resetPassword(String emailId, String oldPassword, String newPassword) {
       try {

            Optional<Admin> existingAdmin = adminRepository.findByEmailId(emailId);

            if (existingAdmin.isPresent()) {
                Admin admin = existingAdmin.get();

                if (admin.getPassword().equals(oldPassword)) {

                    admin.setPassword(newPassword);

                    adminRepository.save(admin);

                    return ResponseEntity.ok().body(new ApiResponse(admin, true, 200, "Password reset successfully"));
               } else {
                   return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(new ApiResponse(false, 400, "password didn't match"));
               }
           } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, 404, "Admin not found with email: " + emailId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, 500, "Internal server error"));
       }*/
  }
