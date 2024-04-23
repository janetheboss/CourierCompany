package com.example.couriercompany.payload;

public record OrderDTO(
        Long statusId,
        Long id,
        String registration,
        String status,
        String nameOfProduct,
        Double price ,
        Double kg,
        String cityTo,
        String cityFrom
) {
}
