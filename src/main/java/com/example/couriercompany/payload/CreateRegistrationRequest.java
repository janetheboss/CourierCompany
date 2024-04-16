package com.example.couriercompany.payload;

public record CreateRegistrationRequest(
     String username,
     String password,
     String role,
     String telephoneNumber,
     String addressForDelivery
){
}

