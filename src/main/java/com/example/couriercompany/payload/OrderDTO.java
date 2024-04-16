package com.example.couriercompany.payload;

public record OrderDTO(
        String registration,
        String status,
        String nameOfProduct,
        Double price ,
        Double kg,
        String cityTo,
        String cityFrom
) {
}
