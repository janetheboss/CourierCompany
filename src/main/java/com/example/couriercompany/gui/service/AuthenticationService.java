package com.example.couriercompany.gui.service;

import com.example.couriercompany.payload.CreateRegistrationRequest;
import com.example.couriercompany.payload.LoginRequestDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AuthenticationService {
    public ResponseEntity<String> fetchSignUp(CreateRegistrationRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateRegistrationRequest> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/api/v1/auth/register";

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<String> fetchLogin(LoginRequestDTO request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/api/v1/auth/login";

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<String> fetchUserRole(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/api/v1/auth/" + username;

        return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }
}
