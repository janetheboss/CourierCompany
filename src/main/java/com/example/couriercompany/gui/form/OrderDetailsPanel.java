package com.example.couriercompany.gui.form;

import com.example.couriercompany.model.Orders;
import com.example.couriercompany.payload.OrderDTO;
import com.example.couriercompany.payload.OrderRequestDTO;
import com.example.couriercompany.services.OrderService;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrderDetailsPanel extends JPanel {
    private final JTextField statusField;
    private final JTextField nameOfProductField;
    private final JTextField priceField;
    private final JTextField kgField;
    private final JTextField cityToField;
    private final JTextField cityFromField;
    private final JTextField usernameField;
    private final JButton updateButton;
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public OrderDetailsPanel(OrderDTO orderDTO) {
        setLayout(new GridLayout(8, 2));

        JLabel usernameTextLabel = new JLabel("Username:");
        usernameField = new JTextField(orderDTO.registration());
        usernameField.setEditable(false);
        add(usernameTextLabel);
        add(usernameField);

        JLabel statusTextLabel = new JLabel("Status:");
        statusField = new JTextField(orderDTO.status());
        add(statusTextLabel);
        add(statusField);

        JLabel nameOfProductTextLabel = new JLabel("Name of Product:");
        nameOfProductField = new JTextField(orderDTO.nameOfProduct());
        add(nameOfProductTextLabel);
        add(nameOfProductField);

        JLabel priceTextLabel = new JLabel("Price:");
        priceField = new JTextField(String.valueOf(orderDTO.price()));
        add(priceTextLabel);
        add(priceField);

        JLabel kgTextLabel = new JLabel("KG:");
        kgField = new JTextField(String.valueOf(orderDTO.kg()));
        add(kgTextLabel);
        add(kgField);

        JLabel cityToTextLabel = new JLabel("City To:");
        cityToField = new JTextField(orderDTO.cityTo());
        add(cityToTextLabel);
        add(cityToField);

        JLabel cityFromTextLabel = new JLabel("City From:");
        cityFromField = new JTextField(orderDTO.cityFrom());
        add(cityFromTextLabel);
        add(cityFromField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateOrder);
        add(updateButton);


        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private void updateOrder(ActionEvent e) {
        try {
            Long orderId = Long.valueOf(usernameField.getText());
            String newStatus = statusField.getText();
            String newNameOfProduct = nameOfProductField.getText();
            double newPrice = Double.parseDouble(priceField.getText());
            double newKg = Double.parseDouble(kgField.getText());
            String newCityTo = cityToField.getText();
            String newCityFrom = cityFromField.getText();

            OrderRequestDTO requestDTO = new OrderRequestDTO(newStatus, newNameOfProduct, newPrice, newKg, newCityTo, newCityFrom);

            ResponseEntity<OrderDTO> response = fetchUpdateDelivery(String.valueOf(orderId), requestDTO);

            if (response.getStatusCode().is2xxSuccessful()) {
                JOptionPane.showMessageDialog(this, "Order updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update order.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price and kg.");
        }
    }

    private ResponseEntity<OrderDTO> fetchUpdateDelivery(String deliveryId, OrderRequestDTO requestDTO) {
        HttpEntity<OrderRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);
        return restTemplate.exchange("/orders/" + deliveryId, HttpMethod.PUT, requestEntity, OrderDTO.class);
    }
}
