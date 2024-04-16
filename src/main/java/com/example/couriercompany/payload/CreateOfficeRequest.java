package com.example.couriercompany.payload;

public record CreateOfficeRequest
        (
                String city,
                String officeName,
                String officeNumber,
                Integer numberOfEmployees
        ){
}
