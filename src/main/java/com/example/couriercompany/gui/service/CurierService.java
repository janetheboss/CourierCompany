package com.example.couriercompany.gui.service;

import com.example.couriercompany.payload.CreateRegistrationCurierRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class CurierService {

    public ResponseEntity<String> fetchCreateCurier(CreateRegistrationCurierRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateRegistrationCurierRequest> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/api/v1/couriers";

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
