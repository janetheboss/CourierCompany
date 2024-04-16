package com.example.couriercompany.services;

import com.example.couriercompany.exception.ResourceNotFoundException;
import com.example.couriercompany.model.Orders;
import com.example.couriercompany.model.Registrations;
import com.example.couriercompany.model.Status;
import com.example.couriercompany.payload.CreateOrderRequest;
import com.example.couriercompany.payload.OrderDTO;
import com.example.couriercompany.repository.OrdersRepository;
import com.example.couriercompany.repository.RegistrationsRepository;
import com.example.couriercompany.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
private final OrdersRepository ordersRepository;
private final RegistrationsRepository registrationsRepository;
private final StatusRepository statusRepository;

    public String createOrder(CreateOrderRequest request) {
        Orders orders = new Orders();
        Registrations user = registrationsRepository.findByUsername(request.username());
        Status status = statusRepository.findByStatusType("Waiting")
            .orElseThrow(() -> new ResourceNotFoundException("Status", "type", "Waiting"));
        orders.setRegistrations(user);
        orders.setStatus(status);
        orders.setNameOfProduct(request.nameOfProduct());
        orders.setPrice(request.price());
        orders.setKg(request.kg());
        orders.setCityTo(request.cityTo());
        orders.setCityFrom(request.cityFrom());

        ordersRepository.save(orders);
        return "Order Created";
    }

    public List<OrderDTO> getAllOrdersFiltered(String username, LocalDate afterDate, LocalDate fiveDaysAgo) {
        Registrations registrations = registrationsRepository.findByUsername(username);


        LocalDateTime convertedDate = null;
        LocalDateTime convertedFiveDaysAgo = null;

        if (afterDate != null) {
            convertedDate = LocalDateTime.of(afterDate.getYear(), afterDate.getMonth(),
                    afterDate.getDayOfMonth(), 0, 0, 0, 0);
        }

        if (fiveDaysAgo != null) {
            convertedFiveDaysAgo = LocalDateTime.of(fiveDaysAgo.getYear(), fiveDaysAgo.getMonth(),
                    fiveDaysAgo.getDayOfMonth(), 0, 0, 0, 0);
        }

        List<Orders> userOrders = ordersRepository.filterOrders(registrations.getUsername(), convertedDate, convertedFiveDaysAgo);

        return mapOrders(userOrders);
    }


    private List<OrderDTO> mapOrders(List<Orders> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Orders order: orders) {
            orderDTOS.add(new OrderDTO(order.getRegistrations().getUsername(),
                    order.getStatus().getStatusInfo(), order.getNameOfProduct(),
                    order.getPrice(), order.getKg(), order.getCityTo(), order.getCityFrom()));
        }
        return orderDTOS;
    }

}
