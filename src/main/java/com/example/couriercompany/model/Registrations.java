package com.example.couriercompany.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registrations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String telephoneNumber;
    private String addressForDelivery;

}
