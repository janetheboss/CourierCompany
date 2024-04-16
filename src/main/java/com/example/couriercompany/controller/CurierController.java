package com.example.couriercompany.controller;

import com.example.couriercompany.payload.CreateRegistrationCurierRequest;
import com.example.couriercompany.services.CreateCurierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CurierController {
    private final CreateCurierService createCurierService;

    @PostMapping
    public ResponseEntity<String> createCurier(@RequestBody CreateRegistrationCurierRequest request) {
        return new ResponseEntity(createCurierService.createCurier(request), HttpStatus.CREATED);
    }
}
