package com.franchiseworld.jalad.serviceimplementation;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.repo.OrderRepository;
import com.franchiseworld.jalad.repo.ZoneRepository;
import com.franchiseworld.jalad.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ZoneServiceImpl implements ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private OrderRepository orderRepository;  //new change

    @Override
    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public Optional<Zone> login(Long zoneCode, String password) {
        return zoneRepository.findByZoneCodeAndPassword(zoneCode, password);
    }
    /*@Override
    public ResponseEntity<ApiResponse> updateZone(Long zoneId, Zone zoneDetail) {
        try
        {
            Optional<Zone> existingZoneDetail= zoneRepository.findById(zoneId);
            if(existingZoneDetail.isPresent())
            {
                Zone zone = existingZoneDetail.get();
                //zone.setZoneId(zoneDetail.getZoneId());  //new change
                zone.setName(zoneDetail.getName());
                zone.setCity(zoneDetail.getCity());
                zone.setState(zoneDetail.getState());
                zone.setPassword(zoneDetail.getPassword());

                // zone.setStatus(zoneDetail.getStatus());  //new comment

                return ResponseEntity.ok().body(new ApiResponse(zoneRepository.save(zone),true,200,"Zone Update"));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,404,"Zone Not Found By Id " + zoneId));
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,500,"Internal Server Error"));
        }

    }*/
    @Override
    public ResponseEntity <List<Orders>> getAllTodayOrder(Long zoneId) {
        //  LocalDate today= LocalDate.now();  //new comment
        List<Orders> order=orderRepository.findOrdersByZoneIdAndOrderDate( zoneId);  //( zoneId, today);
        //List<Orders> order=zoneRepository.findOrdersByZoneIdAndOrderDate( zoneId, today);
        if(order.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
    }

    // Method to get all orders by zone ID
    public List<Orders> getAllOrderByZoneId(Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId).orElse(null);
        if (zone != null) {
            return (List<Orders>) zone.getOrders();    // return zone.getOrders();
        }
        return null; // or throw an exception if zone not found

    }
    // Method to get all orders by zone name
    public List<Orders> getAllOrderByZoneName(String zoneName) {
        Optional<Zone> zoneOptional = zoneRepository.findByName(zoneName);
        if (zoneOptional.isPresent()) {
            return (List<Orders>) zoneOptional.get().getOrders(); // return zoneOptional.get().getOrders();

            // Return the orders if zone is found
        }
        return null; // Consider throwing an exception instead of returning null
    }

    // Method to get Zone by zone ID
    public Zone getZoneById(Long zoneId) {
        Optional<Zone> zone = zoneRepository.findById(zoneId);
        return zone.orElse(null); // Return the zone if found, otherwise return null
    }

    public ResponseEntity<ApiResponse> saveZone(Zone zone)
    {
        try
        {
            return ResponseEntity.ok()
                    .body(new ApiResponse(zoneRepository.save(zone),true,200,"Create Zone"));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false,500,"Internal Server Error"));
        }
    }

    ////change password
/*@Override
public boolean changePassword(Long zoneId, String oldPassword, String newPassword) {
    Optional<Zone> zoneOptional = zoneRepository.findById(zoneId);

    if (zoneOptional.isPresent()) {
        Zone zone = zoneOptional.get();

        // Check if the old password matches
        if (zone.getPassword().equals(oldPassword)) {
            // Update with the new password
            zone.setPassword(newPassword);
            zoneRepository.save(zone);
            return true; // Password changed successfully
        }
    }

    return false; // Password change failed
}*/
    @Override
    public boolean changePassword(String name, String oldPassword, String newPassword,String reEnterNewPassword) {
        //Optional<Zone> zoneOptional = zoneRepository.findById(name);
        Optional<Zone> zoneOptional = zoneRepository.findByName(name);
        if (zoneOptional.isPresent()) {
            Zone zone = zoneOptional.get();

            // Check if the old password matches
            if (zone.getPassword().equals(oldPassword)) {
                // Update with the new password
                zone.setPassword(newPassword);
                zoneRepository.save(zone);
                return true; // Password changed successfully
            }
        }

        return false; // Password change failed
    }

}