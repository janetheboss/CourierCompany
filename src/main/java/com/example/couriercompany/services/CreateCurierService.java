package com.example.couriercompany.services;

import com.example.couriercompany.model.Curier;
import com.example.couriercompany.payload.CreateRegistrationCurierRequest;
import com.example.couriercompany.repository.CurierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCurierService {
    private final CurierRepository curierRepository;
    public String createCurier(CreateRegistrationCurierRequest request) {
        Curier curier = new Curier();
        curier.setCurierName(request.courierName());
        curier.setCurierNumber(request.courierNumber());
        curier.setServiceArea(request.serviceArea());
        curier.setYearsOfExperience(request.yearsOfExperience());

        curierRepository.save(curier);
        return "Courier was created";
    }
}
