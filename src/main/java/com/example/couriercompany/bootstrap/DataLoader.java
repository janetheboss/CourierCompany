package com.example.couriercompany.bootstrap;

import com.example.couriercompany.model.Curier;
import com.example.couriercompany.model.Registrations;
import com.example.couriercompany.model.Status;
import com.example.couriercompany.repository.RegistrationsRepository;
import com.example.couriercompany.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RegistrationsRepository registrationsRepository;
    private final StatusRepository statusRepository;


    @Override
    public void run(String... args) {
        if (registrationsRepository.count() == 0) {
            Status waiting = new Status();
            waiting.setStatusType("Waiting");
            waiting.setStatusInfo("Waiting For Shipment");

            Status inProgress = new Status();
            inProgress.setStatusInfo("DASDAD");
            inProgress.setStatusType("Shipment in progress");

            Status ready = new Status();
            ready.setStatusType("Ready");
            ready.setStatusInfo("Ready");

            statusRepository.saveAll(List.of(waiting, inProgress, ready));

            Registrations registrations = new Registrations();
            registrations.setRole("user");
            registrations.setUsername("jane");
            registrations.setPassword("jane");
            registrations.setTelephoneNumber("+35945345345");
            registrations.setAddressForDelivery("Vkushti");

            Registrations registrations1 = new Registrations();
            registrations.setRole("user");
            registrations.setUsername("kiro");
            registrations.setPassword("kiro");
            registrations.setTelephoneNumber("+35945345345");
            registrations.setAddressForDelivery("Vkushti");

            registrationsRepository.saveAll(List.of(registrations));
            registrationsRepository.saveAll(List.of(registrations1));

        }
    }

}
