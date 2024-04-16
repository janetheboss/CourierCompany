package com.example.couriercompany.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="office")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String officeName;
    private String officeNumber;
    private int numberOfEmployees;

}
