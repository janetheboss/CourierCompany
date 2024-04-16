package com.example.couriercompany.payload;

public record StatusDTO (
        String registration,
        String statusType,
        String statusInfo
) {
}
