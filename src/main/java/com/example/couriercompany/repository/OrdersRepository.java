package com.example.couriercompany.repository;

import com.example.couriercompany.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {


    @Query("SELECT o FROM Orders o " +
            "WHERE o.registrations.username = :username " +
            "AND (cast(:afterDate as timestamp) IS NULL OR o.orderedAt >= :afterDate) " +
            "AND (cast(:fiveDays as timestamp) IS NULL OR o.orderedAt BETWEEN :fiveDays AND CURRENT_TIMESTAMP)")
    List<Orders> filterOrders(String username, LocalDateTime afterDate, LocalDateTime fiveDays);
}
