
package com.franchiseworld.jalad.serviceimplementation;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.franchiseworld.jalad.ResponseHandler.ApiResponse;
import com.franchiseworld.jalad.model.BusinessCourier;
import com.franchiseworld.jalad.model.Orders;
import com.franchiseworld.jalad.model.PersonalCourier;
import com.franchiseworld.jalad.model.Status;
import com.franchiseworld.jalad.model.User;
import com.franchiseworld.jalad.model.Users;
import com.franchiseworld.jalad.model.Zone;
import com.franchiseworld.jalad.modeldto.OrderDto;
import com.franchiseworld.jalad.repo.OrderRepository;
import com.franchiseworld.jalad.repo.UsersRepository;
import com.franchiseworld.jalad.repo.ZoneRepository;
import com.franchiseworld.jalad.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	 @Autowired
	    private UsersRepository usersRepository;

	    @Autowired
	    private ZoneRepository zoneRepository; 
	    
	    @Autowired
	    private OrderRepository orderRepository;

	    @Autowired
	    private UserDetailsServiceImpl userDetailsService;
	    
	 // Get logged-in user
	    private Users getLoggedInUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentUsername = authentication.getName();
	        
	        Optional<User> userOptional = userDetailsService.findByEmail(currentUsername);
	        if (userOptional.isPresent()) {
	            Optional<Users> usersOptional = usersRepository.findByEmail(userOptional.get().getEmail());
	            if (usersOptional.isPresent()) {
	                return usersOptional.get();
	            }
	        }
	        throw new RuntimeException("Logged in user not found");
	    }
	   
    
	    @Override
	    public OrderDto createPersonalCourier(OrderDto orderDto) {
	        // Fetch the authenticated user from the security context
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentUsername = authentication.getName();

	        // Fetch the user details using the username (email)
	        Optional<User> userOptional = userDetailsService.findByEmail(currentUsername);
	        if (userOptional.isEmpty()) {
	            throw new RuntimeException("User not found");
	        }

	        User currentUser = userOptional.get();

	        // Fetch the corresponding Users entity
	        Optional<Users> usersOptional = usersRepository.findByEmail(currentUser.getEmail());
	        if (usersOptional.isEmpty()) {
	            throw new RuntimeException("User entity not found");
	        }

	        Users usersEntity = usersOptional.get();

	        // Fetch all default zones
	        List<Zone> defaultZones = zoneRepository.findDefaultZone();
	        if (defaultZones.isEmpty()) {
	            throw new RuntimeException("No default zones found");
	        }

	        // Choose the first default zone from the list
	        Zone selectedZone = defaultZones.get(0);

	        // Create an Orders entity from the OrderDto
	        PersonalCourier personalCourier = new PersonalCourier();
	        personalCourier.setCreatedBy(orderDto.getCreatedBy());
	        personalCourier.setPickupAddress(orderDto.getPickupAddress());
	        personalCourier.setDeliveryAddress(orderDto.getDeliveryAddress());
	        personalCourier.setContactNo(orderDto.getContactNo());
	        personalCourier.setPackageDetails(orderDto.getPackageDetails());
	        personalCourier.setPackageCover(orderDto.getPackageCover());
	        personalCourier.setPackageSize(orderDto.getPackageSize());
	        personalCourier.setPickupDate(orderDto.getPickupDate());

	        personalCourier.setUsers(usersEntity); // Associate the user
	        personalCourier.setZone(selectedZone); // Set the selected Zone to the order
	        personalCourier.setStatus(Status.DATA_RECEIVED); // Set initial status

	        // Save the order
	        Orders savedOrder = orderRepository.save(personalCourier); // Adjust to your actual repository method

	        // Convert to OrderDto
	        return convertToDto(savedOrder);
	    }
	    
	    @Override
	    public OrderDto createBusinessCourier(OrderDto orderDto) {
	        // Fetch the authenticated user from the security context
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentUsername = authentication.getName();

	        // Fetch the user details using the username (email)
	        Optional<User> userOptional = userDetailsService.findByEmail(currentUsername);
	        if (userOptional.isEmpty()) {
	            throw new RuntimeException("User not found");
	        }

	        User currentUser = userOptional.get();

	        // Fetch the corresponding Users entity
	        Optional<Users> usersOptional = usersRepository.findByEmail(currentUser.getEmail());
	        if (usersOptional.isEmpty()) {
	            throw new RuntimeException("User entity not found");
	        }

	        Users usersEntity = usersOptional.get();

	        // Fetch all default zones
	        List<Zone> defaultZones = zoneRepository.findDefaultZone();
	        if (defaultZones.isEmpty()) {
	            throw new RuntimeException("No default zones found");
	        }

	        // Choose the first default zone from the list
	        Zone selectedZone = defaultZones.get(0);

	        // Create an Orders entity from the OrderDto
	        BusinessCourier businessCourier = new BusinessCourier();
	        businessCourier.setCreatedBy(orderDto.getCreatedBy());
	        businessCourier.setPickupAddress(orderDto.getPickupAddress());
	        businessCourier.setDeliveryAddress(orderDto.getDeliveryAddress());
	        businessCourier.setPackageDetails(orderDto.getPackageDetails());
	        businessCourier.setPackageCover(orderDto.getPackageCover());
	        businessCourier.setPackageSize(orderDto.getPackageSize());
	        businessCourier.setPickupDate(orderDto.getPickupDate());

	        businessCourier.setUsers(usersEntity); // Associate the user
	        businessCourier.setZone(selectedZone); // Set the selected Zone to the order
	        businessCourier.setStatus(Status.DATA_RECEIVED); // Set initial status

	        // Save the order
	        Orders savedOrder = orderRepository.save(businessCourier); // Adjust to your actual repository method

	        // Convert to OrderDto
	        return convertToDto(savedOrder);
	    }
	    // Generalized method to convert Orders to OrderDto
	    private OrderDto convertToDto(Orders order) {
	        OrderDto orderDto = new OrderDto();
	        orderDto.setOrderId(order.getOrderId());
	        orderDto.setCreatedBy(order.getCreatedBy());
	        orderDto.setPickupAddress(order.getPickupAddress());
	        orderDto.setDeliveryAddress(order.getDeliveryAddress());
	        
	        if (order instanceof BusinessCourier) {
	            BusinessCourier businessCourier = (BusinessCourier) order;
	            orderDto.setPackageDetails(businessCourier.getPackageDetails());
	            orderDto.setPackageCover(businessCourier.getPackageCover());
	            orderDto.setPackageSize(businessCourier.getPackageSize());
	            orderDto.setPickupDate(businessCourier.getPickupDate());
	        }else if (order instanceof PersonalCourier) {
	            PersonalCourier personalCourier = (PersonalCourier) order;
	            orderDto.setContactNo(personalCourier.getContactNo());
	            orderDto.setPackageDetails(personalCourier.getPackageDetails());
	            orderDto.setPackageCover(personalCourier.getPackageCover());
	            orderDto.setPackageSize(personalCourier.getPackageSize());
	            orderDto.setPickupDate(personalCourier.getPickupDate());
	        }

//	        if (order instanceof PersonalCourier) {
//	            PersonalCourier personalCourier = (PersonalCourier) order;
//	            orderDto.setContactNo(personalCourier.getContactNo());
//	            orderDto.setPackageDetails(personalCourier.getPackageDetails());
//	            orderDto.setPackageCover(personalCourier.getPackageCover());
//	            orderDto.setPackageSize(personalCourier.getPackageSize());
//	            orderDto.setPickupDate(personalCourier.getPickupDate());
//	        } else if (order instanceof BusinessCourier) {
//	            BusinessCourier businessCourier = (BusinessCourier) order;
//	            orderDto.setPackageDetails(businessCourier.getPackageDetails());
//	            orderDto.setPackageCover(businessCourier.getPackageCover());
//	            orderDto.setPackageSize(businessCourier.getPackageSize());
//	            orderDto.setPickupDate(businessCourier.getPickupDate());
//	        }

	        return orderDto;
	    }

    //FindByContactNO All Courier.
    @Override
    public Page<Object[]> findOrdersByContactNo(String contactNo, Pageable pageable)
    {
        return orderRepository.findByContactNo(contactNo,pageable);
    }

    //FindAllBusinessOrders Only Business Orders
    @Override
    public List<Object[]> findAllBusinessOrders() {
        return orderRepository.findAllBusinessOrders();
    }

    //FindAllBusinessOrdersByUserID Only Business Orders

//    @Override
//    public Page<Object[]> findBusinessOrdersByUserId(Long userId,Pageable pageable)
//    {
//        return orderRepository.findBusinessOrdersByUserId(userId,pageable);
//
//    }
    
    @Override
    public ResponseEntity<Page<Object[]>> findBusinessOrdersForLoggedInUser() {
        Users loggedInUser = getLoggedInUser();
        Pageable pageable = PageRequest.of(0, 10); // Example pagination logic

        Page<Object[]> businessOrders = orderRepository.findBusinessOrdersByUserId(loggedInUser.getId(), pageable);
        return ResponseEntity.ok(businessOrders);
    }

    //Count of StatusOrders(Status:- pending, deliverdy etc)
//    @Override
//    public Map<Status, Long> countBusinessOrdersByStatusAndUserId(Long userId) {
//        List<Object[]> results = orderRepository.countBusinessOrdersByStatusAndUserId(userId);
//        Map<Status, Long> statusCountMap = new HashMap<>();
//
//        for (Object[] result : results) {
//            Status status = (Status) result[1]; // Cast to Status enum
//            Long count = (Long) result[0]; // Cast to Long
//            statusCountMap.put(status, count);
//        }
//
//        return statusCountMap;
//    }
    @Override
    public ResponseEntity<Map<Status, Long>> countBusinessOrdersByStatusForLoggedInUser() {
        Users loggedInUser = getLoggedInUser();
        
        // Fetch the list of status and counts
        List<Object[]> results = orderRepository.countBusinessOrdersByStatusAndUserId(loggedInUser.getId());
        
        // Convert the list into a map
        Map<Status, Long> orderCounts = new HashMap<>();
        for (Object[] result : results) {
            Status status = (Status) result[0];   // The order status
            Long count = (Long) result[1];        // The count of orders
            orderCounts.put(status, count);
        }
        
        return ResponseEntity.ok(orderCounts);
    }


    //GetOrdersDates
    @Override
    public Map<String, LocalDate> getOrderDates(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        Map<String, LocalDate> datesMap = new HashMap<>();

        if (order.getDataReceivedDate() != null) {
            datesMap.put("DATA_RECEIVED", order.getDataReceivedDate());
        }
        if (order.getPickupDoneDate() != null) {
            datesMap.put("PICKUP_DONE", order.getPickupDoneDate());
        }
        if (order.getInTransitDate() != null) {
            datesMap.put("INTRANSIT", order.getInTransitDate());
        }
        if (order.getReachedDestinationDate() != null) {
            datesMap.put("REACHED_DESTINATION", order.getReachedDestinationDate());
        }
        if (order.getOutForDeliveryDate() != null) {
            datesMap.put("OUT_OF_DELIVERY", order.getOutForDeliveryDate());
        }
        if (order.getDeliveredDate() != null) {
            datesMap.put("DELIVERED", order.getDeliveredDate());
        }

        return datesMap;
    }


    //getOrdersByuserid
   /* @Override
    public ResponseEntity<ApiResponse> getOrdersByUserId(Long id) {
        List<Orders> orders = orderRepository.findByUserId(id);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(orders, true, 200, "Courier found successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "Courier not found for id " + id));
        }
    }*/
    //new change 18
    @Override
    public ResponseEntity<ApiResponse> getOrdersByUserId(Long id, Pageable pageable) {
        Page<Orders> ordersPage = orderRepository.findByUserId(id, pageable);
        if (ordersPage.hasContent()) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(ordersPage.getContent(), true, 200, "Orders found successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 404, "No orders found for user id " + id));
        }
    }


    //update status, zone id,zone detail
    @Override
    public Orders updateOrderStatusAndZone(Long orderId, String status, Long zoneId) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();

            // Update the status
            existingOrder.setStatus(Status.valueOf(status.toUpperCase()));

            // Update the zone if the zoneId is different
            if (!existingOrder.getZone().getZoneId().equals(zoneId)) {
                // Fetch the new zone
                Optional<Zone> newZoneOpt = zoneRepository.findById(zoneId);
                if (newZoneOpt.isPresent()) {
                    Zone newZone = newZoneOpt.get();
                    existingOrder.setZone(newZone);
                } else {
                    throw new RuntimeException("Zone not found");
                }
            }

            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getByStatusCount() {
        return null;
    }

    // admin summery
    @Override
    public Map<Status, Long> countOrdersByStatus() {
        List<Object[]> results = orderRepository.countOrdersByStatus();
        Map<Status, Long> statusCountMap = new HashMap<>();

        for (Object[] result : results) {
            Status status = (Status) result[1]; // Cast to Status enum
            Long count = (Long) result[0]; // Cast to Long
            statusCountMap.put(status, count);
        }

        return statusCountMap;
    }

    // update order details crud admin
    @Override
    public ResponseEntity<ApiResponse> updateOrder(Long id, OrderDto orderUpdateDTO) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

            // Update only fields that are not null in the DTO

            if (orderUpdateDTO.getCreatedBy() != null) {
                order.setCreatedBy(orderUpdateDTO.getCreatedBy());
            }

            if (orderUpdateDTO.getPickupAddress() != null) {
                order.setPickupAddress(orderUpdateDTO.getPickupAddress());
            }

            if (orderUpdateDTO.getDeliveryAddress() != null) {
                order.setDeliveryAddress(orderUpdateDTO.getDeliveryAddress());
            }

            // Handle status update and date changes
            if (orderUpdateDTO.getStatus() != null) {
                Status newStatus = Status.valueOf(orderUpdateDTO.getStatus());
                order.setStatus(newStatus); // This will automatically update the dates
            }

            // Update other date fields
            if (orderUpdateDTO.getDataReceivedDate() != null) {
                order.setDataReceivedDate(orderUpdateDTO.getDataReceivedDate());
            }

            if (orderUpdateDTO.getPickupDoneDate() != null) {
                order.setPickupDoneDate(orderUpdateDTO.getPickupDoneDate());
            }

            if (orderUpdateDTO.getInTransitDate() != null) {
                order.setInTransitDate(orderUpdateDTO.getInTransitDate());
            }

            if (orderUpdateDTO.getReachedDestinationDate() != null) {
                order.setReachedDestinationDate(orderUpdateDTO.getReachedDestinationDate());
            }

            if (orderUpdateDTO.getOutForDeliveryDate() != null) {
                order.setOutForDeliveryDate(orderUpdateDTO.getOutForDeliveryDate());
            }

            if (orderUpdateDTO.getDeliveredDate() != null) {
                order.setDeliveredDate(orderUpdateDTO.getDeliveredDate());
            }

            return ResponseEntity.ok().body(new ApiResponse(orderRepository.save(order), true, 200, "updated"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 400, "order not found"));
        }
    }
    // delete order admin
    @Override
    public ResponseEntity<ApiResponse> deleteOrder(Long id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse(optionalOrder,true, 200, "order deleted !"));
                                   //      .body(new ApiResponse(true, 200, "order deleted !"));

        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, 400, "order not found"));
        }
    }

// get all order  (new  change 19 - 09-2024 (Suhas sir)po)
@Override
public ResponseEntity<ApiResponse> getAllOrders( Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Orders> ordersPage = orderRepository.findAll(pageable);

    // Prepare list of orders without 'shippingId'
    List<Map<String, Object>> ordersList = new ArrayList<>();
    for (Orders order : ordersPage.getContent()) {
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("orderId", order.getOrderId());
        orderMap.put("orderDate", order.getOrderDate());
        orderMap.put("createdBy", order.getCreatedBy());
        orderMap.put("pickupAddress", order.getPickupAddress());
        orderMap.put("deliveryAddress", order.getDeliveryAddress());
        ordersList.add(orderMap);
    }

    // Prepare the response with pagination metadata
    Map<String, Object> pagination = new HashMap<>();
    pagination.put("content", ordersList);
    pagination.put("pageNumber", ordersPage.getNumber());
    pagination.put("pageSize", ordersPage.getSize());
    pagination.put("totalPages", ordersPage.getTotalPages());
    pagination.put("totalElements", ordersPage.getTotalElements());
    pagination.put("first", ordersPage.isFirst());
    pagination.put("last", ordersPage.isLast());
    return ResponseEntity.ok()
            .body(new ApiResponse(pagination,true,200,"Orders fetched"));
}
// // Get all personal orders
    @Override
    public List<Object[]> findAllPersonalOrders() {
        return orderRepository.findAllPersonalOrders();
    }
}





