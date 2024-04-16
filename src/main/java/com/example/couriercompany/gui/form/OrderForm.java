package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.service.OrderService;
import com.example.couriercompany.payload.CreateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderForm extends JPanel {
    private JTextField nameOfProductField;
    private JTextField priceField;
    private JTextField kgField;
    private JTextField cityToField;
    private JTextField cityFromField;
    private JButton createButton;

    public OrderForm(String username) {
        setLayout(new GridBagLayout());
        setBackground(new Color(189, 20, 124, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameOfProductField = new JTextField(20);
        priceField = new JTextField(20);
        kgField = new JTextField(20);
        cityToField = new JTextField(20);
        cityFromField = new JTextField(20);
        createButton = new JButton("Create Order");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameOfProduct = nameOfProductField.getText();
                Double price = Double.parseDouble(priceField.getText());
                Double kg = Double.parseDouble(kgField.getText());
                String cityTo = cityToField.getText();
                String cityFrom = cityFromField.getText();
                try {
                    OrderService orderService = new OrderService();
                    ResponseEntity<String> response = orderService.fetchOrder(new CreateOrderRequest("Waiting" ,username, nameOfProduct, price, kg, cityTo, cityFrom));
                    JOptionPane.showMessageDialog(OrderForm.this, response.getBody(), "Successfully created an order!", JOptionPane.INFORMATION_MESSAGE);
                } catch (HttpClientErrorException exception) {
                    JOptionPane.showMessageDialog(OrderForm.this, exception.getResponseBodyAsString(), "Server Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name of Product:"), gbc);
        gbc.gridx = 1;
        add(nameOfProductField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Kg:"), gbc);
        gbc.gridx = 1;
        add(kgField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("City To:"), gbc);
        gbc.gridx = 1;
        add(cityToField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("City From:"), gbc);
        gbc.gridx = 1;
        add(cityFromField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(createButton, gbc);
    }
}
