package com.example.couriercompany.repository;

import com.example.couriercompany.model.Registrations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationsRepository extends JpaRepository<Registrations, Long> {
    Registrations findByUsername(String username);
}
