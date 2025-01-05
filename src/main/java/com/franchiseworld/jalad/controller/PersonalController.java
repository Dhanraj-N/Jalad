package com.franchiseworld.jalad.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.Query;
import com.franchiseworld.jalad.modeldto.OrderDto;
import com.franchiseworld.jalad.modeldto.RateCalculator;
import com.franchiseworld.jalad.modeldto.UsersDto;
import com.franchiseworld.jalad.repo.UsersRepository;
import com.franchiseworld.jalad.repo.ZoneRepository;
import com.franchiseworld.jalad.service.CalculatePriceService;
import com.franchiseworld.jalad.service.OrderService;
import com.franchiseworld.jalad.service.QueryService;
import com.franchiseworld.jalad.service.UsersService;
import com.franchiseworld.jalad.serviceimplementation.UserDetailsServiceImpl;
@RestController
@RequestMapping("/api/personal")
public class PersonalController {

    @Autowired
    private OrderService orderService;  // Use OrderService interface
    @Autowired
    private UsersService usersService;
    @Autowired
    private QueryService queryService;
    @Autowired
    private CalculatePriceService calculatePriceService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse> createPersonalCourier(@RequestBody OrderDto orderDto) {
        try {
            OrderDto createdOrder = orderService.createPersonalCourier(orderDto);
            ApiResponse response = new ApiResponse(true, HttpStatus.OK.value(), "Personal Courier Order Created Successfully", createdOrder);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, 500, "Error creating Personal Courier Order: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // Find all personal orders by contact number
    @GetMapping("/orders/contactNo")
    public Page<Object[]> getOrdersByContactNo(@RequestParam("contactNo") String contactNo,
                                               @RequestParam(value= "page",defaultValue = "0")int page,
                                               @RequestParam(value = "size",defaultValue = "10")int size)
    {
        Pageable pageable= PageRequest.of(page,size);
        return orderService.findOrdersByContactNo(contactNo,pageable);
    }
    //GetOrdersDates
    @GetMapping("/orderdates/{orderId}")
    public ResponseEntity<Map<String, LocalDate>> getOrderDates(@PathVariable Long orderId) {
        Map<String, LocalDate> orderDates = orderService.getOrderDates(orderId);
        return ResponseEntity.ok(orderDates);
    }
    // Update personal user details
    @PutMapping("/users/update")
    public ResponseEntity<ApiResponse> updatePersonalUser(@RequestBody UsersDto userDto) {
        // Get the authenticated user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Delegate update logic to the service layer
        UsersDto updatedUserDto = usersService.updatePersonalUser(currentUsername, userDto);

        if (updatedUserDto == null) {
            return new ResponseEntity<>(new ApiResponse(false, HttpStatus.UNAUTHORIZED.value(), "User not found"), HttpStatus.UNAUTHORIZED);
        }

        // Create a success response
        ApiResponse response = new ApiResponse(true, HttpStatus.OK.value(), "User data updated successfully", updatedUserDto);
        return ResponseEntity.ok(response);
    }
	// create query
    @PostMapping("/query")
    public ResponseEntity<ApiResponse> createQueryByUserId(@RequestParam Long userId, @RequestBody Query query) {
        return queryService.createQueryByUserId(userId, query);
    }

    // Calculate courier price
    @PostMapping("/orders/calculate-price")
    public double calculateCourierPrice(@RequestBody RateCalculator rateCalculator) {
        return calculatePriceService.calculatePrice(rateCalculator);
    }


}
