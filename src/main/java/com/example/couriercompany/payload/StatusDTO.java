package com.example.couriercompany.payload;

public record StatusDTO (
        long id,
        String registration,
        String statusType,
        String statusInfo
) {
}
