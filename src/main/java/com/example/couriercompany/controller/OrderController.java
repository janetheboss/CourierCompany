package com.example.couriercompany.controller;

import com.example.couriercompany.payload.CreateOrderRequest;
import com.example.couriercompany.payload.CreateRegistrationRequest;
import com.example.couriercompany.payload.LoginRequestDTO;
import com.example.couriercompany.payload.OrderDTO;
import com.example.couriercompany.services.AuthenticationService;
import com.example.couriercompany.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {
        private final OrderService orderService;

        @PostMapping("")
        public ResponseEntity<String> createOrder (@RequestBody CreateOrderRequest request)
        {
            return ResponseEntity.ok(orderService.createOrder(request));
        }

        @GetMapping("")
        public ResponseEntity<List<OrderDTO>> filterUserDeliveries(
                @RequestParam String username,
                @RequestParam(required = false) LocalDate afterDate,
                @RequestParam(required = false) LocalDate fiveDays
        ) {
            return ResponseEntity.ok(orderService.getAllOrdersFiltered(username, afterDate, fiveDays));
        }

    }


