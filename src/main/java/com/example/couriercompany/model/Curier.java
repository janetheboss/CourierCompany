package com.example.couriercompany.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="courier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String curierName;
    private String curierNumber;
    private Integer yearsOfExperience;
    private String serviceArea;
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @OneToOne
    @JoinColumn(name = "reg_id")
    private Registrations registrations ;

}
