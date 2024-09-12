package com.franchiseworld.jalad.repo;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.franchiseworld.jalad.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.franchiseworld.jalad.model.Orders;


public interface OrderRepository extends JpaRepository<Orders, Long> {
	//findByContactNo PersonalCouries
	@Query("SELECT DISTINCT o.orderId, o.deliveryAddress, o.status, z.city " +
			"FROM Orders o JOIN o.zone z " +
			"WHERE TYPE(o) = PersonalCourier AND o.contactNo = :contactNo")
	List<Object[]> findByContactNo(@Param("contactNo") Long contactNo);


	//AllBusinessorders
	@Query("SELECT o.orderId, o.orderDate, o.createdBy, o.pickupAddress, o.deliveryAddress, o.status " +
			"FROM Orders o JOIN o.zone z " +
			"WHERE TYPE(o) = BusinessCourier")
	List<Object[]> findAllBusinessOrders();

	//AllBusinessordersbyuserID
	@Query("SELECT o.orderId, o.orderDate, o.createdBy, o.pickupAddress, o.deliveryAddress, o.status " +
			"FROM Orders o " +
			"WHERE TYPE(o) = BusinessCourier AND o.users.id = :userId")
	List<Object[]> findBusinessOrdersByUserId(@Param("userId") Long userId);

	//Count of StatusOrders(Status:- pending, deliverdy etc)
	@Query("SELECT COUNT(o), o.status " +
			"FROM Orders o " +
			"WHERE TYPE(o) = BusinessCourier AND o.users.id = :userId " +
			"GROUP BY o.status")
	List<Object[]> countBusinessOrdersByStatusAndUserId(@Param("userId") Long userId);

	//findordersUsing userId
	@Query("SELECT o FROM Orders o WHERE o.users.id = :id")
	List<Orders> findByUserId(Long id);
	// Find order by orderId date fetch
	Orders findByOrderId(Long orderId);
	//  zone getAllTodayOrder
	@Query("SELECT o FROM Orders o WHERE o.zone.zoneId = :zoneId AND o.orderDate = CURRENT_DATE")
	List<Orders> findOrdersByZoneIdAndOrderDate(@Param("zoneId") Long zoneId);

// admin count summery
	/*@Query("SELECT COUNT(o), o.status " +
			"FROM Orders o " +
			"GROUP BY o.status")
	List<Object[]> countOrdersByStatus();*/
	@Query("SELECT o.status, COUNT(o) " +
			"FROM Orders o " +
			"GROUP BY o.status")
	List<Object[]> countOrdersByStatus();

}







