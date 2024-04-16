package com.example.couriercompany.services;

import com.example.couriercompany.model.Registrations;
import com.example.couriercompany.repository.RegistrationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetClientRole {

    private final RegistrationsRepository registrationsRepository;

    @Autowired
    public GetClientRole(RegistrationsRepository registrationsRepository) {
        this.registrationsRepository = registrationsRepository;
    }
    public String getClientRole(String username) {
        Registrations registration = registrationsRepository.findByUsername(username);
        if (registration != null) {
            return registration.getRole();
        } else {
            return "User not found";
        }
    }
}
