package com.example.couriercompany.payload;

public record OrderRequestDTO (
        Long statusId,
        String status,
        String nameOfProduct,
        Double price ,
        Double kg,
        String cityTo,
        String cityFrom
){
}
