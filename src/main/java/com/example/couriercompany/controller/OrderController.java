package com.example.couriercompany.controller;

import com.example.couriercompany.payload.*;
import com.example.couriercompany.services.AuthenticationService;
import com.example.couriercompany.services.OrderService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
        public ResponseEntity<List<OrderDTO>> filterUserOrders(
                @RequestParam String username,
                @RequestParam(required = false) LocalDate afterDate,
                @RequestParam(required = false) LocalDate fiveDays
        ) {
            return ResponseEntity.ok(orderService.getAllOrdersFiltered(username, afterDate, fiveDays));
        }

    @GetMapping("all")
    public ResponseEntity<List<OrderDTO>> filterOrders(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long statusId
    ) {
        return ResponseEntity.ok(orderService.filterOrders(username, statusId));
    }
    @PutMapping("{deliveryId}")
    public ResponseEntity<OrderDTO> updateDeliveryById(@PathVariable Long deliveryId,
                                                          @RequestBody OrderRequestDTO requestDTO) {
        return ResponseEntity.ok(orderService.updateDelivery(deliveryId, requestDTO));
        }
    }


