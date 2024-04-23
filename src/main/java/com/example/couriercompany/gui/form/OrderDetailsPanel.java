package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.service.OrderService;
import com.example.couriercompany.payload.OrderRequestDTO;
import com.example.couriercompany.payload.OrderDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrderDetailsPanel extends JPanel {
    private final JTextField nameOfProductField;
    private final JTextField priceField;
    private final JTextField kgField;
    private final JTextField cityToField;
    private final JTextField cityFromField;
    private final JButton updateButton;
    private final HttpHeaders headers;

    private JComboBox<String> statusComboBox;

    private final OrderService orderService;

    public OrderDetailsPanel(OrderDTO orderDTO) {
        setLayout(new GridLayout(8, 2));

        JLabel statusTextLabel = new JLabel("Status:");
        statusComboBox = new JComboBox<>(new String[]{"Waiting", "Ready", "Shipping"});
        statusComboBox.setSelectedItem(orderDTO.status());
        add(statusTextLabel);
        add(statusComboBox);

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

        orderService = new OrderService();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private void updateOrder(ActionEvent e) {
        try {
            long newStatusId = (long) statusComboBox.getSelectedItem();
            String newStatus = (String) statusComboBox.getSelectedItem();
            String newNameOfProduct = nameOfProductField.getText();
            double newPrice = Double.parseDouble(priceField.getText());
            double newKg = Double.parseDouble(kgField.getText());
            String newCityTo = cityToField.getText();
            String newCityFrom = cityFromField.getText();

            OrderRequestDTO requestDTO = new OrderRequestDTO(newStatusId, newStatus, newNameOfProduct, newPrice, newKg, newCityTo, newCityFrom);

            ResponseEntity<OrderDTO> response = fetchUpdateDelivery(requestDTO);

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                JOptionPane.showMessageDialog(this, "Order updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update order.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price and kg.");
        }
    }

    private ResponseEntity<OrderDTO> fetchUpdateDelivery(OrderRequestDTO requestDTO) {
        Long deliveryId = Long.parseLong((String) statusComboBox.getSelectedItem());
        Long statusId = requestDTO.statusId();
        requestDTO = new OrderRequestDTO(statusId, requestDTO.status(), requestDTO.nameOfProduct(), requestDTO.price(), requestDTO.kg(), requestDTO.cityTo(), requestDTO.cityFrom());
        return orderService.fetchUpdateDeliveryStatus(deliveryId, requestDTO.statusId());
    }
}