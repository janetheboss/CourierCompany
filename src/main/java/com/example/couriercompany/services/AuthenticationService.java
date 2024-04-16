package com.example.couriercompany.services;

import com.example.couriercompany.exception.APIException;
import com.example.couriercompany.model.Registrations;
import com.example.couriercompany.payload.CreateRegistrationRequest;
import com.example.couriercompany.payload.LoginRequestDTO;
import com.example.couriercompany.repository.RegistrationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RegistrationsRepository registrationsRepository;

    public String createRegistration(CreateRegistrationRequest request) {
        Registrations registrations = new Registrations();
        registrations.setUsername(request.username());
        registrations.setPassword(request.password());
        registrations.setRole(request.role());
        registrations.setTelephoneNumber(request.telephoneNumber());
        registrations.setAddressForDelivery(request.addressForDelivery());

        registrationsRepository.save(registrations);

        return "Registration Created";
    }

    public String login(LoginRequestDTO requestDTO ){
        Optional<Registrations> registrations = Optional.ofNullable(registrationsRepository.findByUsername(requestDTO.username()));

        if(registrations.isPresent()) {
            Registrations user = registrations.get();
            if (user.getPassword().equals(requestDTO.password())) {
                return "Successful login";
            } else {
                throw new APIException(HttpStatus.FORBIDDEN, "Incorrect credentials");
            }
        }
        throw new APIException(HttpStatus.FORBIDDEN, "Incorrect credentials");
    }
}
