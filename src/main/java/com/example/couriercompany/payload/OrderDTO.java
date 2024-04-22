package com.example.couriercompany.payload;

public record OrderDTO(
        Long id,
        Long orderId,
        String registration,
        String status,
        String nameOfProduct,
        Double price ,
        Double kg,
        String cityTo,
        String cityFrom
) {
}
