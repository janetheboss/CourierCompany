package com.example.couriercompany.payload;


public record CreateRegistrationCurierRequest(

         String courierName,
         String courierNumber,
         int yearsOfExperience,
         String serviceArea
){}
