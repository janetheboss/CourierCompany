package com.example.couriercompany.payload;

public record CreateOrderRequest(

        String username,
        String nameOfProduct,
        Double price ,
        Double kg,
        String cityTo,
        String cityFrom
) {
}
