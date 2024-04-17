package com.example.couriercompany.gui.service;
import com.example.couriercompany.payload.CreateOrderRequest;
import com.example.couriercompany.payload.OrderDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

public class OrderService {
    public ResponseEntity<String> fetchOrder(CreateOrderRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateOrderRequest> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/v1/orders";

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<List<OrderDTO>> fetchUserOrders(String username, LocalDate afterDate, LocalDate fiveDays) {
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/v1/orders");
        if (afterDate != null) {
            builder.queryParam("afterDate", afterDate);
        }

        if (fiveDays != null) {
            builder.queryParam("fiveDays", fiveDays);
        }
        builder.queryParam("username", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = builder.toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<List<OrderDTO>> fetchOrdersFiltered(String username, Long statusId) {
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/v1/orders/all");
        if (username != null) {
            builder.queryParam("username", username);
        }
        if (statusId != null && statusId > 0) {
            builder.queryParam("statusId", statusId);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = builder.toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }
}
