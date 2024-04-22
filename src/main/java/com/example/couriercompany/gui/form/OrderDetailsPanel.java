package com.example.couriercompany.gui.form;

import com.example.couriercompany.payload.OrderRequestDTO;
import com.example.couriercompany.payload.OrderDTO;
import com.example.couriercompany.gui.service.OrderService;
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

    private final JComboBox<String> statusComboBox; // Dropdown box for status

    private final OrderService orderService;

    private final Long orderId;

    public OrderDetailsPanel(OrderDTO orderDTO) {
        setLayout(new GridLayout(8, 2));

        // Status Label and ComboBox
        JLabel statusTextLabel = new JLabel("Status:");
        statusComboBox = new JComboBox<>(new String[]{"Waiting", "Ready", "Shipping"});
        statusComboBox.setSelectedItem(orderDTO.status());
        add(statusTextLabel);
        add(statusComboBox);

        // Name of Product Label and Field
        JLabel nameOfProductTextLabel = new JLabel("Name of Product:");
        nameOfProductField = new JTextField(orderDTO.nameOfProduct());
        add(nameOfProductTextLabel);
        add(nameOfProductField);

        // Price Label and Field
        JLabel priceTextLabel = new JLabel("Price:");
        priceField = new JTextField(String.valueOf(orderDTO.price()));
        add(priceTextLabel);
        add(priceField);

        // KG Label and Field
        JLabel kgTextLabel = new JLabel("KG:");
        kgField = new JTextField(String.valueOf(orderDTO.kg()));
        add(kgTextLabel);
        add(kgField);

        // City To Label and Field
        JLabel cityToTextLabel = new JLabel("City To:");
        cityToField = new JTextField(orderDTO.cityTo());
        add(cityToTextLabel);
        add(cityToField);

        // City From Label and Field
        JLabel cityFromTextLabel = new JLabel("City From:");
        cityFromField = new JTextField(orderDTO.cityFrom());
        add(cityFromTextLabel);
        add(cityFromField);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateOrder);
        add(updateButton);

        // Order Service
        orderService = new OrderService();
        this.orderId = orderDTO.id();
    }

    private void updateOrder(ActionEvent e) {
        try {
            String newStatus = (String) statusComboBox.getSelectedItem();
            String newNameOfProduct = nameOfProductField.getText();
            double newPrice = Double.parseDouble(priceField.getText());
            double newKg = Double.parseDouble(kgField.getText());
            String newCityTo = cityToField.getText();
            String newCityFrom = cityFromField.getText();
            OrderRequestDTO requestDTO = new OrderRequestDTO(newStatus, newNameOfProduct, newPrice, newKg, newCityTo, newCityFrom);
            ResponseEntity<OrderDTO> response = orderService.fetchUpdateDelivery(orderId, requestDTO);

            if (response.getStatusCode().is2xxSuccessful()) {
                JOptionPane.showMessageDialog(this, "Order updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update order.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price and kg.");
        }
    }
}
