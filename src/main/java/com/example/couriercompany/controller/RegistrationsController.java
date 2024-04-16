package com.example.couriercompany.controller;

import com.example.couriercompany.payload.CreateRegistrationRequest;
import com.example.couriercompany.payload.LoginRequestDTO;
import com.example.couriercompany.services.AuthenticationService;
import com.example.couriercompany.services.GetClientRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RegistrationsController {
    private final AuthenticationService authenticationService;
    private final GetClientRole getClientRole;

    @PostMapping("register")
    public ResponseEntity<String> createRegistration (@RequestBody CreateRegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.createRegistration(request));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authenticationService.login(requestDTO));
    }
    @GetMapping("/{username}")
    public ResponseEntity<String> getUser(@PathVariable String username) {
        String role = getClientRole.getClientRole(username);
        return ResponseEntity.ok(role);
    }
}
